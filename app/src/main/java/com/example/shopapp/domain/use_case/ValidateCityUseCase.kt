package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.util.Constants.cityEmptyError
import com.example.shopapp.util.Constants.fieldContainsAtLeastOneLetterError
import com.example.shopapp.util.Constants.fieldContainsDigitsError
import com.example.shopapp.util.Constants.fieldContainsSpecialCharsError
import com.example.shopapp.util.Constants.specialCharsCity

class ValidateCityUseCase {
    operator fun invoke(city: String): ValidationResult {
        if(city.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = cityEmptyError
            )
        }
        val containsAtLeastOneLetter = city.any { it.isLetter() }
        if(!containsAtLeastOneLetter) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = fieldContainsAtLeastOneLetterError
            )
        }
        val containsDigit = city.any { it.isDigit() }
        if (containsDigit) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = fieldContainsDigitsError
            )
        }
        val containsSpecialChar = city.any { it in specialCharsCity }
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