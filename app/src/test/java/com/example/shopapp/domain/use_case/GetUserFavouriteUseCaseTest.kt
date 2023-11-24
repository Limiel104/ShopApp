package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.repository.FavouritesRepository
import com.example.shopapp.domain.util.Resource
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

class GetUserFavouriteUseCaseTest {

    @MockK
    private lateinit var favouritesRepository: FavouritesRepository
    private lateinit var getUserFavouriteUseCase: GetUserFavouriteUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getUserFavouriteUseCase = GetUserFavouriteUseCase(favouritesRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getting user favourites was successfully`() {
        runBlocking {
            val favourite = listOf(
                Favourite(
                    favouriteId = "favouriteId1",
                    userUID = "userUID",
                    productId = 1
                )
            )
            val result = Resource.Success(favourite)

            coEvery {
                favouritesRepository.getUserFavourite("userUID",1)
            } returns flowOf(result)

            val response = getUserFavouriteUseCase("userUID",1).first()

            coVerify(exactly = 1) { getUserFavouriteUseCase("userUID",1) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).containsExactlyElementsIn(favourite)
            assertThat(response.message).isNull()
            assertThat(result.data!!.size).isEqualTo(1)
            assertThat(result.data!![0].favouriteId).isEqualTo("favouriteId1")
            assertThat(result.data!![0].userUID).isEqualTo("userUID")
            assertThat(result.data!![0].productId).isEqualTo(1)
        }
    }

    @Test
    fun `getting user favourites was not successful and error message was returned`() {
        runBlocking {
            coEvery {
                favouritesRepository.getUserFavourite("userUID",1)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = getUserFavouriteUseCase("userUID",1).first()

            coVerify(exactly = 1) { getUserFavouriteUseCase("userUID",1) }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}