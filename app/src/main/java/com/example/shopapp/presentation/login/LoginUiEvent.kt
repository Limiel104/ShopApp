package com.example.shopapp.presentation.login

sealed class LoginUiEvent {
    object Login: LoginUiEvent()
    object NavigateToSignup: LoginUiEvent()
    data class ShowErrorMessage(val message: String): LoginUiEvent()
    object NavigateBack: LoginUiEvent()
}