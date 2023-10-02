package com.example.shopapp.presentation.account

sealed class AccountUiEvent {
    object NavigateToLogin: AccountUiEvent()
    object NavigateToSignup: AccountUiEvent()
    object NavigateToCart: AccountUiEvent()
    object NavigateToOrders: AccountUiEvent()
    object NavigateToProfile: AccountUiEvent()
    data class ShowErrorMessage(val message: String): AccountUiEvent()
}