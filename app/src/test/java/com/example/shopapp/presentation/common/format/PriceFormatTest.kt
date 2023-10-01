package com.example.shopapp.presentation.common.format

import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.model.Product
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.util.Date

class PriceFormatTest {

    private lateinit var product: Product
    private lateinit var cartProduct: CartProduct
    private lateinit var order: Order

    @Before
    fun setUp() {
        product = Product(
            id = 1,
            title = "Shirt",
            price = 159.99,
            description = "Product description",
            category = "men's clothing",
            imageUrl = "imageUrl",
            isInFavourites = false
        )

        cartProduct = CartProduct(
            id = 1,
            title = "Polo Shirt",
            price = 55.99,
            imageUrl = "imageUrl",
            amount = 2
        )

        order = Order(
            orderId = "orderId3",
            date = Date(),
            totalAmount = 471.98,
            products = listOf(
                CartProduct(
                    id = 4,
                    title = "Jeans",
                    price = 235.99,
                    imageUrl = "imageUrl",
                    amount = 1
                )
            ),
            isExpanded = false
        )
    }

    @Test
    fun `product price is formatted correctly`() {
        val formattedPrice = product.priceToString()
        assertThat(formattedPrice).isEqualTo("159,99 PLN")
    }

    @Test
    fun `cartProduct price is formatted correctly`() {
        val formattedPrice = cartProduct.priceToString()
        assertThat(formattedPrice).isEqualTo("55,99 PLN")
    }

    @Test
    fun `order price is formatted correctly`() {
        val formattedPrice = order.totalAmountToString()
        assertThat(formattedPrice).isEqualTo("471,98 PLN")
    }
}