package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.AuthRepository
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify

import org.junit.After
import org.junit.Before
import org.junit.Test

class GetCurrentUserUseCaseTest {

    @MockK
    private lateinit var authRepository: AuthRepository
    private lateinit var getCurrentUserUseCase: GetCurrentUserUseCase
    @MockK
    private lateinit var user: FirebaseUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.getCurrentUserUseCase = GetCurrentUserUseCase(authRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `return user if user is logged in`() {
        every { authRepository.currentUser } returns user

        val returnedUser = getCurrentUserUseCase()

        verify(exactly = 1) { getCurrentUserUseCase() }
        assertThat(returnedUser).isNotNull()
        assertThat(returnedUser).isEqualTo(user)
    }

    @Test
    fun `return null if user is not logged in`() {
        every { authRepository.currentUser } returns null

        val returnedUser = getCurrentUserUseCase()

        verify(exactly = 1) { getCurrentUserUseCase() }
        assertThat(returnedUser).isNull()
    }
}