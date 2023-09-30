package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.util.Constants.filedContainsSpecialCharsError
import com.example.shopapp.util.Constants.specialCharsStreet
import com.example.shopapp.util.Constants.streetContainsAtLeastOneDigitError
import com.example.shopapp.util.Constants.fieldContainsAtLeastOneLetterError
import com.example.shopapp.util.Constants.streetEmptyError

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
                errorMessage = filedContainsSpecialCharsError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}