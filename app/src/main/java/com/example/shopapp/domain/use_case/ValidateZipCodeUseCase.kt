package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.ValidationResult
import com.example.shopapp.util.Constants.fieldContainsSpecialCharsError
import com.example.shopapp.util.Constants.specialCharsZipCode
import com.example.shopapp.util.Constants.zipCodeBadFormat
import com.example.shopapp.util.Constants.zipCodeEmptyError
import com.example.shopapp.util.Constants.zipCodeLength

class ValidateZipCodeUseCase {
    operator fun invoke(zipCode: String): ValidationResult {
        if(zipCode.isBlank()) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = zipCodeEmptyError
            )
        }
        if(zipCode.length != zipCodeLength) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = zipCodeBadFormat
            )
        }
        val containsLetters = zipCode.any { it.isLetter() }
        if(containsLetters) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = zipCodeBadFormat
            )
        }
        val containsSpecialChar = zipCode.any { it in specialCharsZipCode }
        if (containsSpecialChar) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = fieldContainsSpecialCharsError
            )
        }
        val isDashInCorrectPlace = zipCode[2] == '-'
        if(!isDashInCorrectPlace) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = zipCodeBadFormat
            )
        }
        val areDigitsInCorrectPlaces = zipCode[0].isDigit() && zipCode[1].isDigit() && zipCode[3].isDigit() && zipCode[4].isDigit() && zipCode[5].isDigit()
        if(!areDigitsInCorrectPlaces) {
            return ValidationResult(
                isSuccessful = false,
                errorMessage = zipCodeBadFormat
            )
        }
        return ValidationResult(
            isSuccessful = true
        )
    }
}