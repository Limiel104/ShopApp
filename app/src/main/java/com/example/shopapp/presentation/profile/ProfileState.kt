package com.example.shopapp.presentation.profile

data class ProfileState(
    val userUID: String = "",
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
    val isLoading: Boolean = false,
    val points: Int = 0
)