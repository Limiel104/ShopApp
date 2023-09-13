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

class AddProductToFavouritesUseCaseTest {

    @MockK
    private lateinit var favouritesRepository: FavouritesRepository
    private lateinit var addProductToFavouritesUseCase: AddProductToFavouritesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addProductToFavouritesUseCase = AddProductToFavouritesUseCase(favouritesRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `product was added to favourites successfully`() {
        runBlocking {
            val productId = 1
            val userUID = "userUID"
            val result = Resource.Success(true)

            coEvery { favouritesRepository.addProductToFavourites(productId,userUID) } returns flowOf(result)

            val response = addProductToFavouritesUseCase(productId,userUID).first()

            coVerify(exactly = 1) { addProductToFavouritesUseCase(productId,userUID) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).isTrue()
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `product was not added to favourites and error message was returned`() {
        runBlocking {
            val productId = 1
            val userUID = "userUID"

            coEvery {
                favouritesRepository.addProductToFavourites(productId,userUID)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = addProductToFavouritesUseCase(productId,userUID).first()

            coVerify(exactly = 1) { addProductToFavouritesUseCase(productId,userUID) }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}