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

class GetUserFavouritesUseCaseTest {

    @MockK
    private lateinit var favouritesRepository: FavouritesRepository
    private lateinit var getUserFavouritesUseCase: GetUserFavouritesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getUserFavouritesUseCase = GetUserFavouritesUseCase(favouritesRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getting user favourites was successfully`() {
        runBlocking {
            val favourites = listOf(
                Favourite(
                    favouriteId = "favouriteId1",
                    userUID = "userUID",
                    productId = 1
                ),
                Favourite(
                    favouriteId = "favouriteId3",
                    userUID = "userUID",
                    productId = 3
                ),
                Favourite(
                    favouriteId = "favouriteId4",
                    userUID = "userUID",
                    productId = 4
                ),
                Favourite(
                    favouriteId = "favouriteId9",
                    userUID = "userUID",
                    productId = 9
                )
            )
            val result = Resource.Success(favourites)

            coEvery { favouritesRepository.getUserFavourites("userUID") } returns flowOf(result)

            val response = getUserFavouritesUseCase("userUID").first()

            coVerify(exactly = 1) { getUserFavouritesUseCase("userUID") }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).containsExactlyElementsIn(favourites)
            assertThat(response.message).isNull()
            for (favourite in response.data!!) {
                assertThat(favourite.userUID).isEqualTo("userUID")
            }
        }
    }

    @Test
    fun `getting user favourites was not successful and error message was returned`() {
        runBlocking {
            coEvery {
                favouritesRepository.getUserFavourites("userUID")
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = getUserFavouritesUseCase("userUID").first()

            coVerify(exactly = 1) { getUserFavouritesUseCase("userUID") }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}