package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.AuthRepository
import com.example.shopapp.util.Resource
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    @MockK
    private lateinit var authRepository: AuthRepository
    private lateinit var loginUseCase: LoginUseCase
    @MockK
    private lateinit var user: FirebaseUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.loginUseCase = LoginUseCase(authRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `login was successful`() {
        runBlocking {
            val email = "email@email.com"
            val password = "Qwerty1+"

            coEvery {
                authRepository.login(email,password)
            } returns flowOf(
                Resource.Success(user)
            )

            val result = loginUseCase(email,password).first()

            coVerify(exactly = 1) { loginUseCase(email,password) }
            assertThat(result.data).isNotNull()
            assertThat(result.data).isEqualTo(user)
        }
    }

    @Test
    fun `login was not successful and error message was returned`() {
        runBlocking {
            val email = "email@email.com"
            val password = "Qwerty1+"

            coEvery {
                authRepository.login(email,password)
            } returns flowOf(
                Resource.Error("Error")
            )

            val result = loginUseCase(email,password).first()

            coVerify(exactly = 1) { loginUseCase(email,password) }
            assertThat(result.message).isEqualTo("Error")
            assertThat(result.data).isNull()
        }
    }
}