package com.example.shopapp.presentation.account

sealed class AccountUiEvent {
    object NavigateToLogin: AccountUiEvent()
    object NavigateToSignup: AccountUiEvent()
    object NavigateToCart: AccountUiEvent()
    object NavigateToOrders: AccountUiEvent()
    data class NavigateToProfile(val userUID: String): AccountUiEvent()
    data class ShowErrorMessage(val message: String): AccountUiEvent()
}