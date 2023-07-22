package com.example.shopapp.domain.use_case

import com.example.shopapp.util.Constants.confirmPasswordError
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class ValidateConfirmPasswordUseCaseTest {

    private lateinit var validateConfirmPasswordUseCase: ValidateConfirmPasswordUseCase

    @Before
    fun setUp() {
        validateConfirmPasswordUseCase = ValidateConfirmPasswordUseCase()
    }

    @Test
    fun `validate password and confirmPassword match`() {
        val password = "Qwerty1+"

        val result = validateConfirmPasswordUseCase(password,password)

        assertThat(result.isSuccessful).isTrue()
    }

    @Test
    fun `validate password and confirmPassword are not hte same and correct errors are returned`() {
        val password = "Qwerty1+"
        val confirmPassword = "Qwerty"

        val result = validateConfirmPasswordUseCase(password,confirmPassword)

        assertThat(result.isSuccessful).isFalse()
        assertThat(result.errorMessage).isEqualTo(confirmPasswordError)

        val result2 = validateConfirmPasswordUseCase(password, "")

        assertThat(result2.isSuccessful).isFalse()
        assertThat(result2.errorMessage).isEqualTo(confirmPasswordError)
    }
}