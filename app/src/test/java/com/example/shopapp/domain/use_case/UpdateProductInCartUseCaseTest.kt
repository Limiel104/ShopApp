package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.repository.CartRepository
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

class UpdateProductInCartUseCaseTest {

    @MockK
    private lateinit var cartRepository: CartRepository
    private lateinit var updateProductInCartUseCase: UpdateProductInCartUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        updateProductInCartUseCase = UpdateProductInCartUseCase(cartRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `product in cart was updated successfully`() {
        runBlocking {
            val cartItem = CartItem(
                cartItemId = "cartItemId",
                userUID = "userUID",
                productId = 1,
                amount = 1
            )
            val result = Resource.Success(true)

            coEvery { cartRepository.updateProductInCart(cartItem) } returns flowOf(result)

            val response = updateProductInCartUseCase(cartItem).first()

            coVerify(exactly = 1) { updateProductInCartUseCase(cartItem) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).isTrue()
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `product in cart was not updated and error message was returned`() {
        runBlocking {
            val cartItem = CartItem(
                cartItemId = "cartItemId",
                userUID = "userUID",
                productId = 1,
                amount = 1
            )

            coEvery {
                cartRepository.updateProductInCart(cartItem)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = updateProductInCartUseCase(cartItem).first()

            coVerify(exactly = 1) { updateProductInCartUseCase(cartItem) }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}