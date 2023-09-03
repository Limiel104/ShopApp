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

class AddProductToCartUseCaseTest {

    @MockK
    private lateinit var cartRepository: CartRepository
    private lateinit var addProductToCartUseCase: AddProductToCartUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addProductToCartUseCase = AddProductToCartUseCase(cartRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `product was added to cart successfully`() {
        runBlocking {
            val userUID = "userUID"
            val productId = 1
            val amount = 1
            val result = Resource.Success(true)

            coEvery { cartRepository.addProductToCart(userUID,productId,amount) } returns flowOf(result)

            val response = addProductToCartUseCase(userUID,productId,amount).first()

            coVerify(exactly = 1) { addProductToCartUseCase(userUID,productId,amount) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).isTrue()
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `product was not added to cart and error message was returned`() {
        runBlocking {
            val userUID = "userUID"
            val productId = 1
            val amount = 1

            coEvery {
                cartRepository.addProductToCart(userUID,productId,amount)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = addProductToCartUseCase(userUID,productId,amount).first()

            coVerify(exactly = 1) { addProductToCartUseCase(userUID,productId,amount) }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}