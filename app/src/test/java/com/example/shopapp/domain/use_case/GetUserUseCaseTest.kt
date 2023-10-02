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

class GetUserUseCaseTest {

    @MockK
    private lateinit var userStorageRepository: UserStorageRepository
    private lateinit var getUserUseCase: GetUserUseCase
    private lateinit var user: User


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getUserUseCase = GetUserUseCase(userStorageRepository)

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
    fun `get user was successful`() {
        runBlocking {
            val result = Resource.Success(listOf(user))

            coEvery {
                userStorageRepository.getUser("userUID")
            } returns flowOf(result)

            val response = getUserUseCase("userUID").first()

            coVerify(exactly = 1) { getUserUseCase("userUID") }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).containsExactlyElementsIn(listOf(user))
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `get user was not successful and error message was returned`() {
        runBlocking {
            coEvery {
                userStorageRepository.getUser("userUID")
            } returns flowOf(Resource.Error("Error"))

            val response = getUserUseCase("userUID").first()

            coVerify(exactly = 1) { getUserUseCase("userUID") }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}