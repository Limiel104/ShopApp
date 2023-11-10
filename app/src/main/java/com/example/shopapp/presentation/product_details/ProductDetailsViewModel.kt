package com.example.shopapp.presentation.product_details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _productDetailsState = mutableStateOf(ProductDetailsState())
    val productDetailsState: State<ProductDetailsState> = _productDetailsState

    private val _eventFlow = MutableSharedFlow<ProductDetailsUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, PRODUCT_DETAILS_VM)

        savedStateHandle.get<Int>("productId")?.let { productId ->
            _productDetailsState.value = productDetailsState.value.copy(
                productId = productId
            )
        }

        getProduct(_productDetailsState.value.productId)
        getCurrentUser()
    }

    fun onEvent(event: ProductDetailsEvent) {
        when(event) {
            is ProductDetailsEvent.OnFavouriteButtonSelected -> {
                if(_productDetailsState.value.isButtonEnabled) {
                    isFavouriteButtonEnabled(false)
                    onFavouriteButtonClicked(_productDetailsState.value.isInFavourites)
                }
            }
            is ProductDetailsEvent.OnAddToCart -> {
                if(_productDetailsState.value.userUID != "") {
                    addOrUpdateProductInCart(
                        _productDetailsState.value.userUID,
                        _productDetailsState.value.productId
                    )
                }
                else {
                    viewModelScope.launch {
                        _eventFlow.emit(ProductDetailsUiEvent.ShowErrorMessage("You need to be logged in"))
                    }
                }
            }
            is ProductDetailsEvent.GoToCart -> {
                viewModelScope.launch {
                    _eventFlow.emit(ProductDetailsUiEvent.NavigateToCart)
                }
            }
            is ProductDetailsEvent.GoBack -> {
                viewModelScope.launch {
                    _eventFlow.emit(ProductDetailsUiEvent.NavigateBack)
                }
            }
        }
    }

    private fun getProduct(productId: Int) {
        viewModelScope.launch {
            when(val response = shopUseCases.getProductUseCase(productId)) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (response.data != null) {
                        _productDetailsState.value = productDetailsState.value.copy(
                            title = response.data.title,
                            price = response.data.price,
                            description = response.data.description,
                            category = response.data.category,
                            imageUrl = response.data.imageUrl
                        )
                    }
                }
                is Resource.Error -> {
                    Log.i(TAG, response.message.toString())
                }
            }
        }
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val currentUser = shopUseCases.getCurrentUserUseCase()
            if(currentUser != null) {
                _productDetailsState.value = productDetailsState.value.copy(
                    userUID = currentUser.uid
                )
            }

            getProductFavouriteId(
                _productDetailsState.value.userUID,
                _productDetailsState.value.productId
            )
        }
    }

    fun getProductFavouriteId(userUID: String, productId: Int) {
        viewModelScope.launch {
            shopUseCases.getUserFavouriteUseCase(userUID,productId).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading favourite: ${response.isLoading}")
                        _productDetailsState.value = productDetailsState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        if (!response.data.isNullOrEmpty()) {
                            _productDetailsState.value = productDetailsState.value.copy(
                                favouriteId = response.data[0].favouriteId,
                                isInFavourites = true
                            )
                            Log.i(TAG,"Product is user in favourites")
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(ProductDetailsUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun addOrUpdateProductInCart(userUID: String, productId: Int) {
        viewModelScope.launch {
            shopUseCases.getUserCartItemUseCase(userUID,productId).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading get user cart item: ${response.isLoading}")
                        _productDetailsState.value = productDetailsState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        if(!response.data.isNullOrEmpty()) {
                            val cartItem = CartItem(
                                cartItemId = response.data[0].cartItemId,
                                userUID = response.data[0].userUID,
                                productId = response.data[0].productId,
                                amount = response.data[0].amount+1
                            )
                            updateProductInCart(cartItem)
                        }
                        else {
                            addProductToCart(userUID,productId)
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(ProductDetailsUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun addProductToCart(userUID: String, productId: Int) {
        viewModelScope.launch {
            shopUseCases.addProductToCartUseCase(userUID,productId,1).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading product add to cart: ${response.isLoading}")
                        _productDetailsState.value = productDetailsState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Product added to the cart")
                        _eventFlow.emit(ProductDetailsUiEvent.ShowProductAddedToCartMessage)
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(ProductDetailsUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun updateProductInCart(cartItem: CartItem) {
        viewModelScope.launch {
            shopUseCases.updateProductInCartUseCase(cartItem).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading product update in cart: ${response.isLoading}")
                        _productDetailsState.value = productDetailsState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Product updated in the cart")
                        _eventFlow.emit(ProductDetailsUiEvent.ShowProductAddedToCartMessage)
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(ProductDetailsUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun isFavouriteButtonEnabled(value: Boolean) {
        Log.i(TAG,"isEnabled - $value")
        _productDetailsState.value = productDetailsState.value.copy(
            isButtonEnabled = value
        )
    }
    private fun onFavouriteButtonClicked(isInFavourites: Boolean) {
        val userUID = _productDetailsState.value.userUID

        if(userUID == null) {
            Log.i(TAG,"User is not logged in - login or signup")
            _productDetailsState.value = productDetailsState.value.copy(
                isDialogActivated = true
            )
            isFavouriteButtonEnabled(true)
        }
        else if(isInFavourites) {
            deleteProductFromUserFavourites(_productDetailsState.value.favouriteId)
        }
        else {
            val productId = _productDetailsState.value.productId
            addProductToUserFavourites(productId,userUID)
        }
    }

    fun addProductToUserFavourites(productId: Int, userUID: String) {
        viewModelScope.launch {
            shopUseCases.addProductToFavouritesUseCase(productId,userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading add favourite: ${response.isLoading}")
                        _productDetailsState.value = productDetailsState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Added product to favourites")
                        getProductFavouriteId(userUID, productId)
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(ProductDetailsUiEvent.ShowErrorMessage(response.message.toString()))
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
                        _productDetailsState.value = productDetailsState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Deleted product from favourites")
                        _productDetailsState.value = productDetailsState.value.copy(
                            isInFavourites = false
                        )
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(ProductDetailsUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
                isFavouriteButtonEnabled(true)
            }
        }
    }
}