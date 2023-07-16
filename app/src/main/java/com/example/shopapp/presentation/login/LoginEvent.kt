package com.example.shopapp.presentation.login

sealed class LoginEvent {
    data class EnteredEmail(val value: String): LoginEvent()
    data class EnteredPassword(val value: String): LoginEvent()
    object OnSignupButtonSelected: LoginEvent()
    object Login: LoginEvent()
}