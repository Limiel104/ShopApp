package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.util.Constants.emailEmptyError

class ValidateEmailUseCase {
    operator fun invoke(email: String): ValidationResult {
        if (email.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = emailEmptyError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}