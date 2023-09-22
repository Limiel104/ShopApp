package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.model.Product
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.util.Date

class SetOrdersUseCaseTest {
    private lateinit var setOrdersUseCase: SetOrdersUseCase
    private lateinit var firebaseOrders: List<FirebaseOrder>
    private lateinit var products: List<Product>
    private lateinit var orders: List<Order>
    private lateinit var date: Date

    @Before
    fun setUp() {
        setOrdersUseCase = SetOrdersUseCase()
        date = Date()

        firebaseOrders = listOf(
            FirebaseOrder(
                orderId = "orderId1",
                userUID = "userUID",
                date = date,
                totalAmount = 290.75,
                products = mapOf(
                    Pair("1",2),
                    Pair("3",1),
                    Pair("6",1)
                )
            ),
            FirebaseOrder(
                orderId = "orderId2",
                userUID = "userUID",
                date = date,
                totalAmount = 347.97,
                products = mapOf(
                    Pair("2",1),
                    Pair("5",3)
                )
            )
        )

        products = listOf(
            Product(
                id = 1,
                title = "Polo Shirt",
                price = 55.99,
                description = Constants.productDescription,
                category = Category.Women.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "Cargo Pants",
                price = 90.00,
                description = Constants.productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Skirt",
                price = 78.78,
                description = Constants.productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Jeans",
                price = 235.99,
                description = Constants.productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 5,
                title = "Shirt",
                price = 85.99,
                description = Constants.productDescription,
                category = Category.Women.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 6,
                title = "Blouse",
                price = 99.99,
                description = Constants.productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            )
        ).shuffled()

        orders = listOf(
            Order(
                orderId = "orderId1",
                date = date,
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
                )
            ),
            Order(
                orderId = "orderId2",
                date = date,
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
                )
            )
        )
    }

    @Test
    fun `orders are set correctly`() {
        val resultOrders = setOrdersUseCase(firebaseOrders,products)

        assertThat(resultOrders).containsExactlyElementsIn(orders)
    }
}