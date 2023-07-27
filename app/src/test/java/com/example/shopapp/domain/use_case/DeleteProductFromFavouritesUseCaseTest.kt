package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.FavouritesRepository
import com.example.shopapp.util.Resource
import com.google.common.truth.Truth
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
        this.deleteProductFromFavouritesUseCase = DeleteProductFromFavouritesUseCase(favouritesRepository)
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
            Truth.assertThat(response).isEqualTo(result)
            Truth.assertThat(response.data).isTrue()
            Truth.assertThat(response.message).isNull()
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
            Truth.assertThat(response.message).isEqualTo("Error")
            Truth.assertThat(response.data).isNull()
        }
    }
}