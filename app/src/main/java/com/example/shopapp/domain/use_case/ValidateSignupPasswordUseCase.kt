package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.util.Constants.passwordContainsAtLeastOneCapitalLetterError
import com.example.shopapp.util.Constants.passwordContainsAtLeastOneDigitError
import com.example.shopapp.util.Constants.passwordContainsAtLeastOneSpecialCharError
import com.example.shopapp.util.Constants.passwordEmptyError
import com.example.shopapp.util.Constants.passwordMinLength
import com.example.shopapp.util.Constants.shortPasswordError
import com.example.shopapp.util.Constants.specialChars

class ValidateSignupPasswordUseCase {
    operator fun invoke(password: String): ValidationResult {
        if(password.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = passwordEmptyError
            )
        }
        if(password.length < passwordMinLength) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = shortPasswordError
            )
        }
        val containsAtLeastOneDigit = password.any { it.isDigit() }
        if(!containsAtLeastOneDigit) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = passwordContainsAtLeastOneDigitError
            )
        }
        val containsAtLeastOneCapitalLetter = password.any { it.isUpperCase() }
        if(!containsAtLeastOneCapitalLetter) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = passwordContainsAtLeastOneCapitalLetterError
            )
        }
        val containsAtLeastOneSpecialChar = password.any { it in specialChars }
        if(!containsAtLeastOneSpecialChar) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = passwordContainsAtLeastOneSpecialCharError
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}