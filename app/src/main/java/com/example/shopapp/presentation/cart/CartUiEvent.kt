package com.example.shopapp.presentation.cart

sealed class CartUiEvent {
    data class ShowErrorMessage(val message: String): CartUiEvent()
    object NavigateToLogin: CartUiEvent()
    object NavigateToSignup: CartUiEvent()
    object NavigateBack: CartUiEvent()
    object ShowSnackbar: CartUiEvent()
    object NavigateToHome: CartUiEvent()
}