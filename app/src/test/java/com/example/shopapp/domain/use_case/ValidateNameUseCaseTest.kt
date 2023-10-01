package com.example.shopapp.domain.use_case

import com.example.shopapp.util.Constants.fieldContainsDigitsError
import com.example.shopapp.util.Constants.fieldEmptyError
import com.example.shopapp.util.Constants.fieldContainsSpecialCharsError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateNameUseCaseTest {

    private lateinit var validateNameUseCase: ValidateNameUseCase

    @Before
    fun setUp() {
        validateNameUseCase = ValidateNameUseCase()
    }

    @Test
    fun `validate name is correct`() {
        val name = "John"

        val result = validateNameUseCase(name)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate name is blank and correct error is returned`() {
        val name = ""

        val result = validateNameUseCase(name)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldEmptyError)
    }

    @Test
    fun `validate name has digit and correct error is returned`() {
        val name = "John4"

        val result = validateNameUseCase(name)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldContainsDigitsError)
    }

    @Test
    fun `validate name has special char and correct error is returned`() {
        val name = "Jo#hn"

        val result = validateNameUseCase(name)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(fieldContainsSpecialCharsError)
    }
}