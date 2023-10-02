package com.example.shopapp.presentation.signup

data class SignupState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,
    val firstName: String = "",
    val firstNameError: String? = null,
    val lastName: String = "",
    val lastNameError: String? = null,
    val street: String = "",
    val streetError: String? = null,
    val city: String = "",
    val cityError: String? = null,
    val zipCode: String = "",
    val zipCodeError: String? = null,
    val isLoading: Boolean = false
)