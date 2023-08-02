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
import com.example.shopapp.util.Constants.CATEGORY_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Constants.categoryId
import com.example.shopapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _categoryState = mutableStateOf(CategoryState())
    val categoryState: State<CategoryState> = _categoryState

    private val _eventFlow = MutableSharedFlow<CategoryUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, CATEGORY_VM)

        savedStateHandle.get<String>(categoryId)?.let { categoryId ->
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
                    _categoryState.value = categoryState.value.copy(
                        productId = event.value
                    )
                    _eventFlow.emit(CategoryUiEvent.NavigateToProductDetails(event.value))
                }
            }
            is CategoryEvent.OnFavouriteButtonSelected -> {
                val selectedProductId = event.value
                val isProductInFavourites = isProductInFavourites(selectedProductId)
                val userUID = _categoryState.value.userUID

                if(userUID == null) {
//                    TODO dialog informing that user is not logged in and needs an account to do this
                    Log.i(TAG,"User is not logged in - login or signup")
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
            is CategoryEvent.ToggleSortSection -> {
                _categoryState.value = categoryState.value.copy(
                    isSortSectionVisible = !_categoryState.value.isSortSectionVisible
                )
            }
        }
    }

    fun checkIfUserIsLoggedIn() {
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
                            setUserFavourites(products,userFavourites)
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(CategoryUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getUserFavourites(userUID: String) {
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
                        _eventFlow.emit(CategoryUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun setUserFavourites(products: List<Product>,favourites: List<Favourite>) {
        _categoryState.value = categoryState.value.copy(
            productList = shopUseCases.setUserFavouritesUseCase(products,favourites)
        )
    }

    fun isProductInFavourites(productId: Int): Boolean {
        val products = _categoryState.value.productList
        val product = products.find { product ->
            product.id == productId
        }

        return product!!.isInFavourites
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
                        _eventFlow.emit(CategoryUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
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
                        _eventFlow.emit(CategoryUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }
}