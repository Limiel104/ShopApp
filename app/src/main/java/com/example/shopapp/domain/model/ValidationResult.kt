package com.example.shopapp.domain.model

data class ValidationResult(
    val isSuccessful: Boolean,
    val errorMessage: String? = null
)