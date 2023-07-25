package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.util.Constants.passwordEmptyError

class ValidateLoginPasswordUseCase {
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = passwordEmptyError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}