package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.CartItem
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

class GetUserCartItemsUseCaseTest {

    @MockK
    private lateinit var cartRepository: CartRepository
    private lateinit var getUserCartItemsUseCase: GetUserCartItemsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getUserCartItemsUseCase = GetUserCartItemsUseCase(cartRepository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getting user cart items was successfully`() {
        runBlocking {
            val cartItems = listOf(
                CartItem(
                    cartItemId = "cartItemId1",
                    userUID = "userUID",
                    productId = 1,
                    amount = 1
                ),
                CartItem(
                    cartItemId = "cartItemId2",
                    userUID = "userUID",
                    productId = 3,
                    amount = 4
                ),
                CartItem(
                    cartItemId = "cartItemId3",
                    userUID = "userUID",
                    productId = 4,
                    amount = 1
                ),
                CartItem(
                    cartItemId = "cartItemId4",
                    userUID = "userUID",
                    productId = 6,
                    amount = 2
                )
            )
            val userUID = "userUID"
            val result = Resource.Success(cartItems)

            coEvery { cartRepository.getUserCartItems(userUID) } returns flowOf(result)

            val response = getUserCartItemsUseCase(userUID).first()

            coVerify(exactly = 1) { getUserCartItemsUseCase(userUID) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).containsExactlyElementsIn(cartItems)
            assertThat(response.message).isNull()
            for (favourite in response.data!!) {
                assertThat(favourite.userUID).isEqualTo(userUID)
                assertThat(favourite)
            }
        }
    }

    @Test
    fun `getting user favourites was not successful and error message was returned`() {
        runBlocking {
            val userUID = "userUID"

            coEvery {
                cartRepository.getUserCartItems(userUID)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = getUserCartItemsUseCase(userUID).first()

            coVerify(exactly = 1) { getUserCartItemsUseCase(userUID) }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}