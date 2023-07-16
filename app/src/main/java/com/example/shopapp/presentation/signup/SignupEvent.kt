package com.example.shopapp.presentation.signup

sealed class SignupEvent {
    data class EnteredEmail(val value: String): SignupEvent()
    data class EnteredPassword(val value: String): SignupEvent()
    data class EnteredConfirmPassword(val value: String): SignupEvent()
    object Signup: SignupEvent()
}