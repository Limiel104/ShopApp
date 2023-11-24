package com.example.shopapp.domain.use_case

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

class DeleteProductFromFavouritesUseCaseTest {

    @MockK
    private lateinit var favouritesRepository: FavouritesRepository
    private lateinit var deleteProductFromFavouritesUseCase: DeleteProductFromFavouritesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deleteProductFromFavouritesUseCase = DeleteProductFromFavouritesUseCase(favouritesRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `product was deleted from favourites successfully`() {
        runBlocking {
            val favouriteId = "favouriteId"
            val result = Resource.Success(true)

            coEvery { favouritesRepository.deleteProductFromFavourites(favouriteId) } returns flowOf(result)

            val response = deleteProductFromFavouritesUseCase(favouriteId).first()

            coVerify(exactly = 1) { deleteProductFromFavouritesUseCase(favouriteId) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).isTrue()
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `product was not deleted from favourites and error message was returned`() {
        runBlocking {
            val favouriteId = "favouriteId"

            coEvery {
                favouritesRepository.deleteProductFromFavourites(favouriteId)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = deleteProductFromFavouritesUseCase(favouriteId).first()

            coVerify(exactly = 1) { deleteProductFromFavouritesUseCase(favouriteId) }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}