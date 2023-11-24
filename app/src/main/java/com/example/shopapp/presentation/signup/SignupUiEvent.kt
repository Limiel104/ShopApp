package com.example.shopapp.presentation.signup

sealed class SignupUiEvent {
    object Signup: SignupUiEvent()
    data class ShowErrorMessage(val message: String): SignupUiEvent()
    object NavigateBack: SignupUiEvent()
}