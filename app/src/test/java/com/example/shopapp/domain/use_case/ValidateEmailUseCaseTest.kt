package com.example.shopapp.domain.use_case

import com.example.shopapp.presentation.common.Constants.emailEmptyError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateEmailUseCaseTest {

    private lateinit var validateEmailUseCase: ValidateEmailUseCase

    @Before
    fun setUp() {
        validateEmailUseCase = ValidateEmailUseCase()
    }

    @Test
    fun `validate email is correct`() {
        val email = "email@email.com"

        val result = validateEmailUseCase(email)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate email is blank and correct error is returned`() {
        val email = ""

        val result = validateEmailUseCase(email)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(emailEmptyError)
    }
}