package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.util.OrderOrder
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.util.Date

class SortOrdersUseCaseTest {
    private lateinit var sortOrdersUseCase: SortOrdersUseCase
    private lateinit var orders: List<Order>
    private lateinit var date1: Date
    private lateinit var date2: Date
    private lateinit var date3: Date

    @Before
    fun setUp() {
        sortOrdersUseCase = SortOrdersUseCase()

        date1 = Date(2023,9,18)
        date2 = Date(2023,9,21)
        date3 = Date(2023,9,27)

        orders = listOf(
            Order(
                orderId = "orderId1",
                date = date3,
                totalAmount = 290.75,
                products = listOf(
                    CartProduct(
                        id = 1,
                        title = "Polo Shirt",
                        price = 55.99,
                        imageUrl = "imageUrl",
                        amount = 2
                    ),
                    CartProduct(
                        id = 3,
                        title = "Skirt",
                        price = 78.78,
                        imageUrl = "imageUrl",
                        amount = 1
                    ),
                    CartProduct(
                        id = 6,
                        title = "Blouse",
                        price = 99.99,
                        imageUrl = "imageUrl",
                        amount = 1
                    )
                ),
                isExpanded = false
            ),
            Order(
                orderId = "orderId2",
                date = date1,
                totalAmount = 347.97,
                products = listOf(
                    CartProduct(
                        id = 2,
                        title = "Cargo Pants",
                        price = 90.00,
                        imageUrl = "imageUrl",
                        amount = 1
                    ),
                    CartProduct(
                        id = 5,
                        title = "Shirt",
                        price = 85.99,
                        imageUrl = "imageUrl",
                        amount = 3
                    )
                ),
                isExpanded = false
            ),
            Order(
                orderId = "orderId2",
                date = date2,
                totalAmount = 54.00,
                products = listOf(
                    CartProduct(
                        id = 6,
                        title = "Blouse",
                        price = 99.99,
                        imageUrl = "imageUrl",
                        amount = 2
                    ),
                    CartProduct(
                        id = 5,
                        title = "Shirt",
                        price = 85.99,
                        imageUrl = "imageUrl",
                        amount = 3
                    )
                ),
                isExpanded = false
            )
        )
    }

    @Test
    fun `orders are sorted by date asc correctly`() {
        val resultOrders = sortOrdersUseCase(OrderOrder.DateAscending(),orders)

        for(i in 0..resultOrders.size-2) {
            assertThat(resultOrders[i].date).isLessThan(resultOrders[i+1].date)
            println(resultOrders[i].date.toString())
        }
    }

    @Test
    fun `orders are sorted by date desc correctly`() {
        val resultOrders = sortOrdersUseCase(OrderOrder.DateDescending(),orders)

        for(i in 0..resultOrders.size-2) {
            assertThat(resultOrders[i].date).isGreaterThan(resultOrders[i+1].date)
            println(resultOrders[i].date.toString())
        }
    }
}