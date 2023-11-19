package com.example.shopapp.presentation.cart

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.CART_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _cartState = mutableStateOf(CartState())
    val cartState: State<CartState> = _cartState

    private val _cartEventChannel = Channel<CartUiEvent>()
    val cartEventChannelFlow = _cartEventChannel.receiveAsFlow()

    private var deletedCartItem: CartItem? = null

    init {
        Log.i(TAG, CART_VM)

        checkIfUserIsLoggedIn()

        if(_cartState.value.isUserLoggedIn) {
            getUserCartItems(_cartState.value.userUID)
            getUserCoupon(_cartState.value.userUID)
        }
    }

    fun onEvent(event: CartEvent) {
        when(event) {
            is CartEvent.OnLogin -> {
                viewModelScope.launch {
                    _cartEventChannel.send(CartUiEvent.NavigateToLogin)
                }
            }
            is CartEvent.OnSignup -> {
                viewModelScope.launch {
                    _cartEventChannel.send(CartUiEvent.NavigateToSignup)
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
                    _cartEventChannel.send(CartUiEvent.NavigateBack)
                }
            }
            is CartEvent.OnDelete -> {
                val cartItemToDelete = getCartItemFromCartProductId(event.value)
                deletedCartItem = cartItemToDelete
                deleteCartItem(cartItemToDelete.cartItemId)
                viewModelScope.launch {
                    _cartEventChannel.send(CartUiEvent.ShowSnackbar)
                }
            }
            is CartEvent.OnCartItemRestore -> {
                Log.i(TAG,deletedCartItem.toString())
                deletedCartItem?.let { restoreProductToCart(it) }
            }
            is CartEvent.OnOrderPlaced -> {
                val firebaseOrder = FirebaseOrder(
                    orderId = "",
                    userUID = _cartState.value.userUID,
                    date = Date(),
                    totalAmount = _cartState.value.totalAmount,
                    products = getProductsMap()
                )
                addOrder(firebaseOrder)
            }
            is CartEvent.OnGoHome -> {
                viewModelScope.launch {
                    _cartState.value = cartState.value.copy(
                        isDialogActivated = false
                    )

                    _cartEventChannel.send(CartUiEvent.NavigateToHome)
                }
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
                        _cartEventChannel.send(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getProducts() {
        viewModelScope.launch {
            shopUseCases.getProductsUseCase("all").collect { response ->
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
                        _cartEventChannel.send(CartUiEvent.ShowErrorMessage(response.message.toString()))
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

    fun getUserCoupon(userUID: String) {
        viewModelScope.launch {
            shopUseCases.getUserCouponUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading coupon: ${response.isLoading}")
                        _cartState.value = cartState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { coupon ->
                            val isCouponAlreadyExpired = shopUseCases.isCouponExpiredUseCase(
                                coupon.activationDate,
                                Calendar.getInstance().time
                            )
                            if(!isCouponAlreadyExpired) {
                                Log.i(TAG,"Coupon: $coupon")
                                _cartState.value = _cartState.value.copy(
                                    isCouponActivated = true,
                                    coupon = coupon
                                )
                            }
                            else {
                                deleteCoupon(userUID)
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _cartEventChannel.send(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun deleteCoupon(userUID: String) {
        viewModelScope.launch {
            shopUseCases.deleteCouponUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading delete coupon: ${response.isLoading}")
                        _cartState.value = cartState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Expired coupon was deleted")
                        _cartState.value = _cartState.value.copy(
                            isCouponActivated = false
                        )
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _cartEventChannel.send(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun calculateTotalAmount() {
        var totalAmount = 0.0
        val cartProducts = _cartState.value.cartProducts
        val couponAmount = _cartState.value.coupon.amount

        for(cartProduct in cartProducts) {
            totalAmount += cartProduct.amount * cartProduct.price
        }

        if(_cartState.value.isCouponActivated && (totalAmount >= couponAmount+1)) {
            _cartState.value = cartState.value.copy(
                totalAmount = totalAmount - couponAmount,
                isCouponUsedInCalculatingTotalAmount = true
            )
        }
        else {
            _cartState.value = cartState.value.copy(
                totalAmount = totalAmount,
                isCouponUsedInCalculatingTotalAmount = false
            )
        }
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
                        _cartEventChannel.send(CartUiEvent.ShowErrorMessage(response.message.toString()))
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
                        _cartEventChannel.send(CartUiEvent.ShowErrorMessage(response.message.toString()))
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
                        _cartEventChannel.send(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getProductsMap(): Map<String, Int> {
        val products = mutableMapOf<String,Int>()

        _cartState.value.cartProducts.forEach { cartProduct ->
            products[cartProduct.id.toString()] = cartProduct.amount
        }

        return products
    }

    fun addOrder(firebaseOrder: FirebaseOrder) {
        viewModelScope.launch {
            shopUseCases.addOrderUseCase(firebaseOrder).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading add order: ${response.isLoading}")
                        _cartState.value = cartState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Order was placed")
                        _cartState.value = cartState.value.copy(
                            isDialogActivated = true
                        )
                        for(cartItem in _cartState.value.cartItems) {
                            deleteCartItem(cartItem.cartItemId)
                            val wasCouponUsedForThisOrder = _cartState.value.isCouponActivated && _cartState.value.isCouponUsedInCalculatingTotalAmount
                            if(wasCouponUsedForThisOrder) {
                                deleteCoupon(_cartState.value.userUID)
                                _cartState.value = cartState.value.copy(
                                    isCouponActivated = false
                                )
                            }
                        }
                        getUserPoints(_cartState.value.userUID)
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _cartEventChannel.send(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun getUserPoints(userUID: String) {
        viewModelScope.launch {
            shopUseCases.getUserPointsUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading get user points: ${response.isLoading}")
                        _cartState.value = cartState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { userPoints ->
                            val userPointsAfterPlacingOrder = userPoints + _cartState.value.totalAmount.toInt()

                            updateUserPoints(
                                _cartState.value.userUID,
                                userPointsAfterPlacingOrder
                            )
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _cartEventChannel.send(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun updateUserPoints(userUID: String, points: Int) {
        viewModelScope.launch {
            shopUseCases.updateUserPointsUseCase(userUID,points).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading update user points: ${response.isLoading}")
                        _cartState.value = cartState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"User points were updated")
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _cartEventChannel.send(CartUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }
}