package com.example.shopapp.presentation.category

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.domain.util.ProductOrder
import com.example.shopapp.presentation.common.Constants.CATEGORY_VM
import com.example.shopapp.presentation.common.Constants.TAG
import com.example.shopapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _categoryState = mutableStateOf(CategoryState())
    val categoryState: State<CategoryState> = _categoryState

    private val _categoryEventChannel = Channel<CategoryUiEvent>()
    val categoryEventChannelFlow = _categoryEventChannel.receiveAsFlow()

    init {
        Log.i(TAG, CATEGORY_VM)

        savedStateHandle.get<String>("categoryId")?.let { categoryId ->
            _categoryState.value = categoryState.value.copy(
                categoryId = categoryId
            )
        }

        checkIfUserIsLoggedIn()
    }

    fun onEvent(event: CategoryEvent) {
        when(event) {
            is CategoryEvent.OnProductSelected -> {
                viewModelScope.launch {
                    _categoryEventChannel.send(CategoryUiEvent.NavigateToProductDetails(event.value))
                }
            }
            is CategoryEvent.OnFavouriteButtonSelected -> {
                if(_categoryState.value.isButtonEnabled) {
                    isFavouriteButtonEnabled(false)
                    onFavouriteButtonClicked(event.value)
                }
            }
            is CategoryEvent.OnPriceSliderPositionChange -> {
                _categoryState.value = categoryState.value.copy(
                    priceSliderPosition = event.value
                )
                getProducts()
            }
            is CategoryEvent.OnOrderChange -> {
                sortProducts(event.value)
            }
            is CategoryEvent.OnCheckBoxToggled -> {
                toggleCheckBox(event.value)
                getProducts()
            }
            is CategoryEvent.ToggleSortAndFilterSection -> {
                _categoryState.value = categoryState.value.copy(
                    isSortAndFilterSectionVisible = !_categoryState.value.isSortAndFilterSectionVisible
                )
            }
            is CategoryEvent.OnDialogDismissed -> {
                _categoryState.value = categoryState.value.copy(
                    isDialogActivated = false
                )
            }
            is CategoryEvent.GoToCart -> {
                viewModelScope.launch {
                    _categoryEventChannel.send(CategoryUiEvent.NavigateToCart)
                }
            }
        }
    }

    private fun checkIfUserIsLoggedIn() {
        val user = shopUseCases.getCurrentUserUseCase()
        if(user != null) {
            _categoryState.value = categoryState.value.copy(
                userUID = user.uid
            )
            getUserFavourites(user.uid)
        }
        else {
            getProducts()
        }
    }

    fun getProducts(
        categoryId: String = _categoryState.value.categoryId,
        userFavourites: List<Favourite> = _categoryState.value.userFavourites
    ) {
        viewModelScope.launch {
            shopUseCases.getProductsUseCase(categoryId).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading products: ${response.isLoading}")
                        _categoryState.value = categoryState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { products ->
                            Log.i(TAG, "Products: $products")
                            if(products.isNotEmpty()) {
                                setUserFavourites(products, userFavourites)
                                if(!_categoryState.value.isRangeSet) {
                                    setPriceSlider()
                                }
                                filterProducts()
                                sortProducts(_categoryState.value.productOrder)
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _categoryEventChannel.send(CategoryUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    private fun getUserFavourites(userUID: String) {
        viewModelScope.launch {
            shopUseCases.getUserFavouritesUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading user favourites: ${response.isLoading}")
                        _categoryState.value = categoryState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Favourites: ${response.data}")
                        response.data?.let { favourites ->
                            _categoryState.value = categoryState.value.copy(
                                userFavourites = favourites
                            )
                        }
                        getProducts()
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _categoryEventChannel.send(CategoryUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    private fun setUserFavourites(products: List<Product>, favourites: List<Favourite>) {
        _categoryState.value = categoryState.value.copy(
            productList = shopUseCases.setUserFavouritesUseCase(products,favourites)
        )
    }

    fun isProductInFavourites(productId: Int): Boolean {
        val products = _categoryState.value.productList
        val product = products.find { product ->
            product.id == productId
        }

        return product?.isInFavourites ?: false
    }

    fun isFavouriteButtonEnabled(value: Boolean) {
        Log.i(TAG,"isEnabled - $value")
        _categoryState.value = categoryState.value.copy(
            isButtonEnabled = value
        )
    }

    private fun onFavouriteButtonClicked(selectedProductId: Int) {
        val isProductInFavourites = isProductInFavourites(selectedProductId)
        val userUID = _categoryState.value.userUID

        if(userUID == null) {
            Log.i(TAG,"User is not logged in - login or signup")
            _categoryState.value = categoryState.value.copy(
                isDialogActivated = true
            )
            isFavouriteButtonEnabled(true)
        }
        else if(isProductInFavourites) {
            val favourites = _categoryState.value.userFavourites
            val favouriteId = shopUseCases.getFavouriteIdUseCase(favourites,selectedProductId)
            deleteProductFromUserFavourites(favouriteId)
        }
        else {
            addProductToUserFavourites(selectedProductId,userUID)
        }
    }

    fun addProductToUserFavourites(productId: Int, userUID: String) {
        viewModelScope.launch {
            shopUseCases.addProductToFavouritesUseCase(productId,userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading add favourite: ${response.isLoading}")
                        _categoryState.value = categoryState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Added product to favourites")
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _categoryEventChannel.send(CategoryUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
                isFavouriteButtonEnabled(true)
            }
        }
    }

    fun deleteProductFromUserFavourites(favouriteId: String) {
        viewModelScope.launch {
            shopUseCases.deleteProductFromFavouritesUseCase(favouriteId).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading delete favourite: ${response.isLoading}")
                        _categoryState.value = categoryState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Deleted product from favourites")
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _categoryEventChannel.send(CategoryUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
                isFavouriteButtonEnabled(true)
            }
        }
    }

    fun setPriceSlider() {
        val prices = _categoryState.value.productList.sortedBy { it.price }
        val startValue = prices.first().price.toFloat()
        val endValue = prices.last().price.toFloat()

        _categoryState.value = categoryState.value.copy(
            priceSliderPosition = startValue..endValue,
            priceSliderRange = startValue..endValue,
            isRangeSet = true
        )
    }

    fun filterProducts() {
        val products = shopUseCases.filterProductsUseCase(
            products = _categoryState.value.productList,
            minPrice = _categoryState.value.priceSliderPosition.start,
            maxPrice = _categoryState.value.priceSliderPosition.endInclusive,
            categoryFilterMap = _categoryState.value.categoryFilterMap
        )

        _categoryState.value = _categoryState.value.copy(
            productList = products
        )
    }

    fun sortProducts(productOrder: ProductOrder) {
        _categoryState.value = categoryState.value.copy(
            productOrder = productOrder
        )

        _categoryState.value = categoryState.value.copy(
            productList = shopUseCases.sortProductsUseCase(
                productOrder = productOrder,
                products = _categoryState.value.productList
            )
        )
    }

    fun toggleCheckBox(selectedCheckBox: String) {
        val map = _categoryState.value.categoryFilterMap

        _categoryState.value = categoryState.value.copy(
            categoryFilterMap = shopUseCases.toggleCheckBoxUseCase(map,selectedCheckBox)
        )
    }
}