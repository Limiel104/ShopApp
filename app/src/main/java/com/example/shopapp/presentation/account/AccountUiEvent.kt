package com.example.shopapp.presentation.account

sealed class AccountUiEvent {
    object NavigateToLogin: AccountUiEvent()
    object NavigateToSignup: AccountUiEvent()
}