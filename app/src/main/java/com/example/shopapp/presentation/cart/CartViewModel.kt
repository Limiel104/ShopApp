package com.example.shopapp.presentation.cart

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.CART_VM
import com.example.shopapp.util.Constants.TAG
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

    init {
        Log.i(TAG, CART_VM)

        checkIfUserIsLoggedIn()
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
}