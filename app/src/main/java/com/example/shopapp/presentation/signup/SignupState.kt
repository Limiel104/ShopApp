package com.example.shopapp.presentation.signup

data class SignupState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)