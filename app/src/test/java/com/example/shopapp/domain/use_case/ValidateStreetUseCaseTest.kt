package com.example.shopapp.domain.use_case

import com.example.shopapp.presentation.common.Constants.fieldContainsAtLeastOneLetterError
import com.example.shopapp.presentation.common.Constants.fieldContainsSpecialCharsError
import com.example.shopapp.presentation.common.Constants.streetContainsAtLeastOneDigitError
import com.example.shopapp.presentation.common.Constants.streetEmptyError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateStreetUseCaseTest {

    private lateinit var validateStreetUseCase: ValidateStreetUseCase

    @Before
    fun setUp() {
        validateStreetUseCase = ValidateStreetUseCase()
    }

    @Test
    fun `validate street is correct - contains slash`() {
        val street = "Street 1/"

        val result = validateStreetUseCase(street)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate street is correct - contains -`() {
        val street = "Street-Street 1"

        val result = validateStreetUseCase(street)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate street is correct - contains '`() {
        val street = "Street's 1"

        val result = validateStreetUseCase(street)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate street is correct - contains all`() {
        val street = "Street-Street's 1/34"

        val result = validateStreetUseCase(street)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate street is blank and correct error is returned`() {
        val street = ""

        val result = validateStreetUseCase(street)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(streetEmptyError)
    }

    @Test
    fun `validate street does not have at least one letter and correct error is returned`() {
        val street = "245[]"

        val result = validateStreetUseCase(street)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldContainsAtLeastOneLetterError)
    }

    @Test
    fun `validate street does not have at least one digit`() {
        val street = "Street"

        val result = validateStreetUseCase(street)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(streetContainsAtLeastOneDigitError)
    }

    @Test
    fun `validate city has special char and correct error is returned - contains backslash`() {
        val street = "Street 1\\4"

        val result = validateStreetUseCase(street)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldContainsSpecialCharsError)
    }
}