package com.example.shopapp.domain.use_case

import com.example.shopapp.presentation.common.Constants.fieldContainsSpecialCharsError
import com.example.shopapp.presentation.common.Constants.zipCodeBadFormat
import com.example.shopapp.presentation.common.Constants.zipCodeEmptyError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateZipCodeUseCaseTest {
    private lateinit var validateZipCodeUseCase: ValidateZipCodeUseCase

    @Before
    fun setUp() {
        validateZipCodeUseCase = ValidateZipCodeUseCase()
    }

    @Test
    fun `validate zip code is correct`() {
        val zipCode = "12-345"

        val result = validateZipCodeUseCase(zipCode)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate zip code is blank and correct error is returned`() {
        val zipCode = ""

        val result = validateZipCodeUseCase(zipCode)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(zipCodeEmptyError)
    }

    @Test
    fun `validate zip code has incorrect length and correct error is returned`() {
        val zipCode = "12-"

        val result = validateZipCodeUseCase(zipCode)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(zipCodeBadFormat)
    }

    @Test
    fun `validate zip code has letters and correct error is returned`() {
        val zipCode = "12A345"

        val result = validateZipCodeUseCase(zipCode)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(zipCodeBadFormat)
    }

    @Test
    fun `validate zip code has special char and correct error is returned - contains '`() {
        val zipCode = "12'345"

        val result = validateZipCodeUseCase(zipCode)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldContainsSpecialCharsError)
    }

    @Test
    fun `validate zip code has special char and correct error is returned - contains backslash`() {
        val zipCode = "12\\345"

        val result = validateZipCodeUseCase(zipCode)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldContainsSpecialCharsError)
    }

    @Test
    fun `validate zip code has special char and correct error is returned - contains slash`() {
        val zipCode = "12/345"

        val result = validateZipCodeUseCase(zipCode)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldContainsSpecialCharsError)
    }

    @Test
    fun `validate zip code - is in incorrect spot`() {
        val zipCode = "1-2345"

        val result = validateZipCodeUseCase(zipCode)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(zipCodeBadFormat)
    }

    @Test
    fun `validate zip number in incorrect spot`() {
        val zipCode = "123456"

        val result = validateZipCodeUseCase(zipCode)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(zipCodeBadFormat)
    }

    @Test
    fun `validate zip code - in incorrect spots`() {
        val zipCode = "-2-34-"

        val result = validateZipCodeUseCase(zipCode)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(zipCodeBadFormat)
    }
}