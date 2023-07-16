package com.example.shopapp.presentation.login

sealed class LoginUiEvent {
    object Login: LoginUiEvent()
    data class ShowErrorMessage(val value: String): LoginUiEvent()
}