package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.CartRepository
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

class DeleteProductFromCartUseCaseTest {

    @MockK
    private lateinit var cartRepository: CartRepository
    private lateinit var deleteProductFromCartUseCase: DeleteProductFromCartUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deleteProductFromCartUseCase = DeleteProductFromCartUseCase(cartRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `product was deleted from cart successfully`() {
        runBlocking {
            val cartItemId = "cartItemId"
            val result = Resource.Success(true)

            coEvery { cartRepository.deleteProductFromCart(cartItemId) } returns flowOf(result)

            val response = deleteProductFromCartUseCase(cartItemId).first()

            coVerify(exactly = 1) { deleteProductFromCartUseCase(cartItemId) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).isTrue()
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `product was not deleted from cart and error message was returned`() {
        runBlocking {
            val cartItemId = "cartItemId"

            coEvery {
                cartRepository.deleteProductFromCart(cartItemId)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = deleteProductFromCartUseCase(cartItemId).first()

            coVerify(exactly = 1) { deleteProductFromCartUseCase(cartItemId) }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}