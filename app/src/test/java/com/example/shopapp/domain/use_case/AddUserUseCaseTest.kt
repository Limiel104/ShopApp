package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Address
import com.example.shopapp.domain.model.User
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
    private lateinit var user: User

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.addUserUseCase = AddUserUseCase(userStorageRepository)

        user = User(
            userUID = "userUID",
            firstName = "John",
            lastName = "Smith",
            address = Address(
                street = "Street 1",
                city = "Warsaw",
                zipCode = "12-345"
            ),
            points = 0
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `user was added successfully`() {
        runBlocking {
            val result = Resource.Success(true)

            coEvery { userStorageRepository.addUser(user) } returns flowOf(result)

            val response = addUserUseCase(user).first()

            coVerify(exactly = 1) { addUserUseCase(user) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).isTrue()
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `user was not added and error was returned`() {
        runBlocking {
            coEvery {
                userStorageRepository.addUser(user)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = addUserUseCase(user).first()

            coVerify(exactly = 1) { addUserUseCase(user) }
            assertThat(response.message).isEqualTo("Error")
            assertThat(response.data).isNull()
        }
    }
}