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

class GetUserCartItemUseCaseTest {

    @MockK
    private lateinit var cartRepository: CartRepository
    private lateinit var getUserCartItemUseCase: GetUserCartItemUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getUserCartItemUseCase = GetUserCartItemUseCase(cartRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun`getting user cart items was successfully`() {
        runBlocking {
            val cartItem = listOf(
                CartItem(
                    cartItemId = "cartItemId1",
                    userUID = "userUID",
                    productId = 1,
                    amount = 1
                )
            )
            val userUID = "userUID"
            val productId = 1
            val result = Resource.Success(cartItem)

            coEvery { cartRepository.getUserCartItem(userUID,productId) } returns flowOf(result)

            val response = getUserCartItemUseCase(userUID,productId).first()

            coVerify(exactly = 1) { getUserCartItemUseCase(userUID,productId) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).containsExactlyElementsIn(cartItem)
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `getting user favourites was not successful and error message was returned`() {
        runBlocking {
            val userUID = "userUID"
            val productId = 1

            coEvery {
                cartRepository.getUserCartItem(userUID,productId)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = getUserCartItemUseCase(userUID,productId).first()

            coVerify(exactly = 1) { getUserCartItemUseCase(userUID,productId) }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}