package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.FavouritesRepository
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

class GetFavouriteIdUseCaseTest {

    @MockK
    private lateinit var favouritesRepository: FavouritesRepository
    private lateinit var getFavouriteIdUseCase: GetFavouriteIdUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        this.getFavouriteIdUseCase = GetFavouriteIdUseCase(favouritesRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getting favourite id was successfully`() {
        runBlocking {
            val productId = 1
            val userUID = "userUID"
            val favouriteId = "favouriteId"
            val result = Resource.Success(favouriteId)

            coEvery { favouritesRepository.getFavouriteId(productId,userUID) } returns flowOf(result)

            val response = getFavouriteIdUseCase(productId,userUID).first()

            coVerify(exactly = 1) { getFavouriteIdUseCase(productId,userUID) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).isEqualTo(favouriteId)
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `getting favourite id was not successful and error message was returned`() {
        runBlocking {
            val productId = 1
            val userUID = "userUID"

            coEvery {
                favouritesRepository.getFavouriteId(productId,userUID)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = getFavouriteIdUseCase(productId,userUID).first()

            coVerify(exactly = 1) { getFavouriteIdUseCase(productId,userUID) }
            assertThat(response.message).isEqualTo("Error")
            assertThat(response.data).isNull()
        }
    }
}