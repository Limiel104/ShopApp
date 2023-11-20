package com.example.shopapp.domain.use_case

import com.example.shopapp.presentation.common.Constants.passwordContainsAtLeastOneCapitalLetterError
import com.example.shopapp.presentation.common.Constants.passwordContainsAtLeastOneDigitError
import com.example.shopapp.presentation.common.Constants.passwordContainsAtLeastOneSpecialCharError
import com.example.shopapp.presentation.common.Constants.passwordEmptyError
import com.example.shopapp.presentation.common.Constants.shortPasswordError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateSignupPasswordUseCaseTest {

    private lateinit var validateSignupPasswordUseCase: ValidateSignupPasswordUseCase

    @Before
    fun setUp() {
        validateSignupPasswordUseCase = ValidateSignupPasswordUseCase()
    }

    @Test
    fun `validate password is correct`() {
        val password = "Qwerty1+"

        val result = validateSignupPasswordUseCase(password)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate password is blank and correct error is returned`() {
        val password = ""

        val result = validateSignupPasswordUseCase(password)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(passwordEmptyError)
    }

    @Test
    fun `validate password is too short and correct error is returned`() {
        val password = "Qwert"

        val result = validateSignupPasswordUseCase(password)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(shortPasswordError)
    }

    @Test
    fun `validate password does not have at least one digit and correct error is returned`() {
        val password = "Qwerty++"

        val result = validateSignupPasswordUseCase(password)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(passwordContainsAtLeastOneDigitError)
    }

    @Test
    fun  `validate password does not have at least one capital letter and correct error is returned`() {
        val password = "qwerty1+"

        val result = validateSignupPasswordUseCase(password)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(passwordContainsAtLeastOneCapitalLetterError)
    }

    @Test
    fun `validate password does not have at least one special char and correct error is returned`() {
        val password = "Qwerty11"

        val result = validateSignupPasswordUseCase(password)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(passwordContainsAtLeastOneSpecialCharError)
    }
}