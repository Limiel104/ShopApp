package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.UserStorageRepository
import com.example.shopapp.util.Resource
import com.google.common.truth.Truth.assertThat
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

class AddUserUseCaseTest {

    @MockK
    private lateinit var userStorageRepository: UserStorageRepository
    private lateinit var addUserUseCase: AddUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.addUserUseCase = AddUserUseCase(userStorageRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `user was added successfully`() {
        runBlocking {
            val userUID = "userUID"
            val result = Resource.Success(true)

            coEvery { userStorageRepository.addUser(userUID) } returns flowOf(result)

            val response = addUserUseCase(userUID).first()

            coVerify(exactly = 1) { addUserUseCase(userUID) }
            assertThat(response).isEqualTo(result)
        }
    }

    @Test
    fun `user was not added and error was returned`() {
        runBlocking {
            val userUID = "userUID"

            coEvery {
                userStorageRepository.addUser(userUID)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = addUserUseCase(userUID).first()

            coVerify(exactly = 1) { addUserUseCase(userUID) }
            assertThat(response.message).isEqualTo("Error")
            assertThat(response.data).isNull()
        }
    }
}