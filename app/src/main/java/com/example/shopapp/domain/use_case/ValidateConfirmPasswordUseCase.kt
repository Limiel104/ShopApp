package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.util.Constants.confirmPasswordError

class ValidateConfirmPasswordUseCase {
    operator fun invoke(password: String, confirmPassword: String): ValidationResult {
        if (password != confirmPassword) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = confirmPasswordError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}