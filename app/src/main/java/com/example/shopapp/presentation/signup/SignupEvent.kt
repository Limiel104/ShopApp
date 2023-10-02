package com.example.shopapp.presentation.signup

sealed class SignupEvent {
    data class EnteredEmail(val value: String): SignupEvent()
    data class EnteredPassword(val value: String): SignupEvent()
    data class EnteredConfirmPassword(val value: String): SignupEvent()
    data class EnteredFirstName(val value: String): SignupEvent()
    data class EnteredLastName(val value: String): SignupEvent()
    data class EnteredStreet(val value: String): SignupEvent()
    data class EnteredCity(val value: String): SignupEvent()
    data class EnteredZipCode(val value: String): SignupEvent()
    object Signup: SignupEvent()
}