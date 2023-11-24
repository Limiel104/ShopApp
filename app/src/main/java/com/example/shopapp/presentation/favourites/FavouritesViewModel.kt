package com.example.shopapp.presentation.favourites

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.presentation.common.Constants.FAVOURITES_VM
import com.example.shopapp.presentation.common.Constants.TAG
import com.example.shopapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _favouritesState = mutableStateOf(FavouritesState())
    val favouritesState: State<FavouritesState> = _favouritesState

    private val _favouritesEventChannel = Channel<FavouritesUiEvent>()
    val favouritesEventChannelFlow = _favouritesEventChannel.receiveAsFlow()

    init {
        Log.i(TAG, FAVOURITES_VM)

        checkIfUserIsLoggedIn()

        if(_favouritesState.value.isUserLoggedIn) {
            getUserFavourites(_favouritesState.value.userUID)
        }
    }

    fun onEvent(event: FavouritesEvent) {
        when(event) {
            is FavouritesEvent.OnProductSelected -> {
                viewModelScope.launch {
                    _favouritesState.value = favouritesState.value.copy(
                        productId = event.value
                    )
                    _favouritesEventChannel.send(FavouritesUiEvent.NavigateToProductDetails(event.value))
                }
            }
            is FavouritesEvent.OnLogin -> {
                viewModelScope.launch {
                    _favouritesEventChannel.send(FavouritesUiEvent.NavigateToLogin)
                }
            }
            is FavouritesEvent.OnSignup -> {
                viewModelScope.launch {
                    _favouritesEventChannel.send(FavouritesUiEvent.NavigateToSignup)
                }
            }
            is FavouritesEvent.OnDelete -> {
                val favourites = _favouritesState.value.favouriteList
                val favouriteId = shopUseCases.getFavouriteIdUseCase(favourites, event.value)
                deleteProductFromFavourites(favouriteId)
            }
            is FavouritesEvent.OnAddToCart -> {
                viewModelScope.launch {
                    val productToAddToCartId = event.value

                    addOrUpdateProductInCart(
                        _favouritesState.value.userUID,
                        productToAddToCartId
                    )
                }
            }
            is FavouritesEvent.GoToCart -> {
                viewModelScope.launch {
                    _favouritesEventChannel.send(FavouritesUiEvent.NavigateToCart)
                }
            }
        }
    }

    private fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val currentUser = shopUseCases.getCurrentUserUseCase()

            _favouritesState.value = favouritesState.value.copy(
                isUserLoggedIn = currentUser != null
            )

            if(currentUser != null) {
                _favouritesState.value = favouritesState.value.copy(
                    userUID = currentUser.uid
                )
            }
        }
    }

    private fun getUserFavourites(userUID: String) {
        viewModelScope.launch {
            shopUseCases.getUserFavouritesUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading favourites: ${response.isLoading}")
                        _favouritesState.value = favouritesState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { favourites ->
                            _favouritesState.value = favouritesState.value.copy(
                                favouriteList = favourites
                            )
                            if(favourites.isNotEmpty()) {
                                getProducts("all")
                            }
                            else {
                                _favouritesState.value = favouritesState.value.copy(
                                    productList = emptyList()
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _favouritesEventChannel.send(FavouritesUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getProducts(categoryId: String) {
        viewModelScope.launch {
            shopUseCases.getProductsUseCase(categoryId).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading products: ${response.isLoading}")
                        _favouritesState.value = favouritesState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { products ->
                            if(products.isNotEmpty()) {
                                getFavouriteProducts(products)
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _favouritesEventChannel.send(FavouritesUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    private fun getFavouriteProducts(products: List<Product>) {
        val favourites = _favouritesState.value.favouriteList

        _favouritesState.value = favouritesState.value.copy(
            productList = shopUseCases.filterProductsByUserFavouritesUseCase(products,favourites)
        )
    }

    fun deleteProductFromFavourites(favouriteId: String) {
        viewModelScope.launch {
            shopUseCases.deleteProductFromFavouritesUseCase(favouriteId).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading delete favourite: ${response.isLoading}")
                        _favouritesState.value = favouritesState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Deleted product from favourites")
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _favouritesEventChannel.send(FavouritesUiEvent.ShowErrorMessage(response.message.toString()))
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
                        _favouritesState.value = favouritesState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        if (!response.data.isNullOrEmpty()) {
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
                        _favouritesEventChannel.send(FavouritesUiEvent.ShowErrorMessage(response.message.toString()))
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
                        Log.i(TAG,"Loading product from favourites to add to cart: ${response.isLoading}")
                        _favouritesState.value = favouritesState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Product added to the cart")
                        _favouritesEventChannel.send(FavouritesUiEvent.ShowProductAddedToCartMessage)
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _favouritesEventChannel.send(FavouritesUiEvent.ShowErrorMessage(response.message.toString()))
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
                        Log.i(TAG,"Loading product from favourites to update in cart: ${response.isLoading}")
                        _favouritesState.value = favouritesState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Product updated in the cart")
                        _favouritesEventChannel.send(FavouritesUiEvent.ShowProductAddedToCartMessage)
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _favouritesEventChannel.send(FavouritesUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }
}