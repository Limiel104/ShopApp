package com.example.shopapp.domain.use_case

import com.example.shopapp.presentation.common.Constants.cityEmptyError
import com.example.shopapp.presentation.common.Constants.fieldContainsAtLeastOneLetterError
import com.example.shopapp.presentation.common.Constants.fieldContainsDigitsError
import com.example.shopapp.presentation.common.Constants.fieldContainsSpecialCharsError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateCityUseCaseTest {

    private lateinit var validateCityUseCase: ValidateCityUseCase

    @Before
    fun setUp() {
        validateCityUseCase = ValidateCityUseCase()
    }

    @Test
    fun `validate city is correct`() {
        val city = "Warsaw"

        val result = validateCityUseCase(city)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate city is correct - contains -`() {
        val city = "War-saw"

        val result = validateCityUseCase(city)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate city is correct - contains '`() {
        val city = "Warsaw's"

        val result = validateCityUseCase(city)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate city is blank and correct error is returned`() {
        val city = ""

        val result = validateCityUseCase(city)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(cityEmptyError)
    }

    @Test
    fun `validate city does not have at least one letter and correct error is returned`() {
        val city = "245[]"

        val result = validateCityUseCase(city)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldContainsAtLeastOneLetterError)
    }

    @Test
    fun `validate city has digit and correct error is returned`() {
        val city = "Wars4w"

        val result = validateCityUseCase(city)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldContainsDigitsError)
    }

    @Test
    fun `validate city has special char and correct error is returned - contains backslash`() {
        val city = "Wars\\aw"

        val result = validateCityUseCase(city)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldContainsSpecialCharsError)
    }

    @Test
    fun `validate city has special char and correct error is returned - contains slash`() {
        val city = "Wars/aw"

        val result = validateCityUseCase(city)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldContainsSpecialCharsError)
    }
}