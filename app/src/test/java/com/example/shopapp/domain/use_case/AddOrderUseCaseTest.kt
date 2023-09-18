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

class AddOrderUseCaseTest {

    @MockK
    private lateinit var ordersRepository: OrdersRepository
    private lateinit var addOrderUseCase: AddOrderUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        addOrderUseCase = AddOrderUseCase(ordersRepository)
    }

    @After
    fun teaDown() {
        clearAllMocks()
    }

    @Test
    fun `order was added to successfully`() {
        runBlocking {
            val order = Order(
                orderId = "orderId",
                userUID = "userUID",
                date = Date(),
                totalAmount = 180.99,
                products = mapOf(
                    Pair("3",5),
                    Pair("12",2)                )
            )
            val result = Resource.Success(true)

            coEvery { ordersRepository.addOrder(order) } returns flowOf(result)
            val response = addOrderUseCase(order).first()

            coVerify(exactly = 1) { addOrderUseCase(order) }
            assertThat(response).isEqualTo(result)
            assertThat(response.data).isTrue()
            assertThat(response.message).isNull()
        }
    }

    @Test
    fun `order was not added and error was returned`() {
        runBlocking {
            val order = Order(
                orderId = "orderId",
                userUID = "userUID",
                date = Date(),
                totalAmount = 180.99,
                products = mapOf(
                    Pair("3",5),
                    Pair("12",2)                )
            )

            coEvery { ordersRepository.addOrder(order) } returns flowOf(Resource.Error("Error"))
            val response = addOrderUseCase(order).first()

            coVerify(exactly = 1) { addOrderUseCase(order) }
            assertThat(response.data).isNull()
            assertThat(response.message).isEqualTo("Error")
        }
    }
}