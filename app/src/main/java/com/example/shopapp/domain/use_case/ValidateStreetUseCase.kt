package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.presentation.common.Constants.fieldContainsSpecialCharsError
import com.example.shopapp.presentation.common.Constants.specialCharsStreet
import com.example.shopapp.presentation.common.Constants.streetContainsAtLeastOneDigitError
import com.example.shopapp.presentation.common.Constants.fieldContainsAtLeastOneLetterError
import com.example.shopapp.presentation.common.Constants.streetEmptyError

class ValidateStreetUseCase {
    operator fun invoke(street: String): ValidationResult {
        if(street.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = streetEmptyError
            )
        }
        val containsAtLeastOneLetter = street.any { it.isLetter() }
        if(!containsAtLeastOneLetter) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = fieldContainsAtLeastOneLetterError
            )
        }
        val containsAtLeastOneDigit = street.any { it.isDigit() }
        if(!containsAtLeastOneDigit) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = streetContainsAtLeastOneDigitError
            )
        }
        val containsSpecialChar = street.any { it in specialCharsStreet }
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