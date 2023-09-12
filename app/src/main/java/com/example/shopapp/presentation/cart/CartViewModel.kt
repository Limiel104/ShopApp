package com.example.shopapp.presentation.cart

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants.CART_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState

    private val _eventFlow = MutableSharedFlow<CartUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var deletedCartItem: CartItem? = null

    init {
        Log.i(TAG, CART_VM)

        checkIfUserIsLoggedIn()

        if(_cartState.value.isUserLoggedIn) {
            getUserCartItems(_cartState.value.userUID)
        }
    }

    fun onEvent(event: CartEvent) {
        when(event) {
            is CartEvent.OnLogin -> {
                viewModelScope.launch {
                    _eventFlow.emit(CartUiEvent.NavigateToLogin)
                }
            }
            is CartEvent.OnSignup -> {
                viewModelScope.launch {
                    _eventFlow.emit(CartUiEvent.NavigateToSignup)
                }
            }
            is CartEvent.OnPlus -> {
                val cartItemToUpdate = getCartItemFromCartProductId(event.value)
                updateCartItem(cartItemToUpdate,cartItemToUpdate.amount+1)
            }
            is CartEvent.OnMinus -> {
                val cartItemToUpdate = getCartItemFromCartProductId(event.value)
                if(cartItemToUpdate.amount == 1) {
                    deleteCartItem(cartItemToUpdate.cartItemId)
                }
                else {
                    updateCartItem(cartItemToUpdate,cartItemToUpdate.amount-1)
                }
            }
            is CartEvent.OnGoBack -> {
                viewModelScope.launch {
                    _eventFlow.emit(CartUiEvent.NavigateBack)
                }
            }
            is CartEvent.OnDelete -> {
                val cartItemToDelete = getCartItemFromCartProductId(event.value)
                deletedCartItem = cartItemToDelete
                deleteCartItem(cartItemToDelete.cartItemId)
                viewModelScope.launch {
                    _eventFlow.emit(CartUiEvent.ShowSnackbar)
                }
            }
            is CartEvent.OnCartItemRestore -> {
                Log.i(TAG,deletedCartItem.toString())
                deletedCartItem?.let { restoreProductToCart(it) }
            }
        }
    }

    private fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val currentUser = shopUseCases.getCurrentUserUseCase()

            _cartState.value = cartState.value.copy(
                isUserLoggedIn = currentUser != null
            )

            if(currentUser != null) {
                _cartState.value = cartState.value.copy(
                    userUID = currentUser.uid
                )
            }
        }
    }

    fun getUserCartItems(userUID: String) {
        viewModelScope.launch {
            shopUseCases.getUserCartItemsUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading products: ${response.isLoading}")
                        _cartState.value = cartState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { cartItems ->
                            Log.i(TAG, "Cart: $cartItems")
                            _cartState.value = cartState.value.copy(
                                cartItems = cartItems
                            )
                            if(cartItems.isNotEmpty()) {
                                getProducts()
                            }
                            else {
                                getCartProducts(emptyList())
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            shopUseCases.getProductsUseCase(Category.All.id).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading products: ${response.isLoading}")
                        _cartState.value = cartState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { products ->
                            if(products.isNotEmpty()) {
                                getCartProducts(products)
                                calculateTotalAmount()
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getCartProducts(products: List<Product>) {
        val cartItems = _cartState.value.cartItems

        _cartState.value = cartState.value.copy(
            cartProducts = shopUseCases.setUserCartProductsUseCase(cartItems,products)
        )
    }

    fun calculateTotalAmount() {
        var totalAmount = 0.0
        val cartProducts = _cartState.value.cartProducts

        for(cartProduct in cartProducts) {
            totalAmount += cartProduct.amount * cartProduct.price
        }

        _cartState.value = cartState.value.copy(
            totalAmount = totalAmount
        )
    }

    fun getCartItemFromCartProductId(cartProductId: Int): CartItem {
        val cartItems = _cartState.value.cartItems
        return cartItems.first { it.productId == cartProductId }
    }

    fun updateCartItem(cartItem: CartItem, newAmount: Int) {
        val cartItemToUpdate = CartItem(
            cartItemId = cartItem.cartItemId,
            userUID = cartItem.userUID,
            productId = cartItem.productId,
            amount = newAmount
        )

        viewModelScope.launch {
            shopUseCases.updateProductInCartUseCase(cartItemToUpdate).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading products: ${response.isLoading}")
                        _cartState.value = cartState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Product amount updated")
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun deleteCartItem(cartItemId: String) {
        viewModelScope.launch {
            shopUseCases.deleteProductFromCartUseCase(cartItemId).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading products: ${response.isLoading}")
                        _cartState.value = cartState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {

                        Log.i(TAG,"Product was deleted from cart")
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun restoreProductToCart(cartItem: CartItem) {
        viewModelScope.launch {
            shopUseCases.addProductToCartUseCase(
                cartItem.userUID,
                cartItem.productId,
                cartItem.amount
            ).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading product restore to cart: ${response.isLoading}")
                        _cartState.value = cartState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Product restored to the cart")
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }
}