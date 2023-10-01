package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.util.Constants.fieldContainsDigitsError
import com.example.shopapp.util.Constants.fieldEmptyError
import com.example.shopapp.util.Constants.specialChars
import com.example.shopapp.util.Constants.fieldContainsSpecialCharsError

class ValidateNameUseCase {
    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = fieldEmptyError
            )
        }
        val containsDigit = name.any { it.isDigit() }
        if (containsDigit) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = fieldContainsDigitsError
            )
        }
        val containsSpecialChar = name.any { it in specialChars }
        if (containsSpecialChar) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = fieldContainsSpecialCharsError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}