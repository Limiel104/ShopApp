package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.repository.OrdersRepository
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
import java.util.Date

class GetUserOrdersUseCaseTest {

    @MockK
    private lateinit var ordersRepository: OrdersRepository
    private lateinit var getUserOrdersUseCase: GetUserOrdersUseCase
    private lateinit var orders: List<Order>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getUserOrdersUseCase = GetUserOrdersUseCase(ordersRepository)

        orders = listOf(
            Order(
                orderId = "orderId1",
                userUID = "userUID",
                date = Date(),
                totalAmount = 123.43,
                products = mapOf(
                    Pair("3",5),
                    Pair("6",1),
                    Pair("7",1)
                )
            ),
            Order(
                orderId = "orderId2",
                userUID = "userUID",
                date = Date(),
                totalAmount = 54.00,
                products = mapOf(
                    Pair("2",1),
                    Pair("8",1),
                    Pair("9",3)
                )
            ),
            Order(
                orderId = "orderId3",
                userUID = "userUID",
                date = Date(),
                totalAmount = 73.99,
                products = mapOf(
                    Pair("1",1),
                    Pair("10",1)
                )
            )
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun`getting user orders was successful`() {
        runBlocking {
            val userUID = "userUID"
            val result = Resource.Success(orders)

            coEvery { ordersRepository.getUserOrders(userUID) } returns flowOf(result)

            val response = getUserOrdersUseCase(userUID).first()

            coVerify(exactly = 1) { getUserOrdersUseCase(userUID) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).containsExactlyElementsIn(orders)
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `getting user orders was not successful and error message was returned`() {
        runBlocking {
            val userUID = "userUID"

            coEvery {
                ordersRepository.getUserOrders(userUID)
            } returns flowOf(
                Resource.Error("Error")
            )

            val response = getUserOrdersUseCase(userUID).first()

            coVerify(exactly = 1) { getUserOrdersUseCase(userUID) }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}