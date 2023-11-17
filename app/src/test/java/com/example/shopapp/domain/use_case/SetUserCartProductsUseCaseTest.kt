package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Product
import com.example.shopapp.util.Constants.productDescription
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class SetUserCartProductsUseCaseTest {

    private lateinit var setUserCartProductsUseCase: SetUserCartProductsUseCase
    private lateinit var cartItems: List<CartItem>
    private lateinit var products: List<Product>
    private lateinit var cartProducts: List<CartProduct>

    @Before
    fun setUp() {
        setUserCartProductsUseCase = SetUserCartProductsUseCase()

        cartItems = listOf(
            CartItem(
                cartItemId = "cartItemId1",
                userUID = "userUID",
                productId = 1,
                amount = 2
            ),
            CartItem(
                cartItemId = "cartItemId2",
                userUID = "userUID",
                productId = 3,
                amount = 1
            ),
            CartItem(
                cartItemId = "cartItemId3",
                userUID = "userUID",
                productId = 4,
                amount = 3
            ),
            CartItem(
                cartItemId = "cartItemId4",
                userUID = "userUID",
                productId = 6,
                amount = 7
            )
        ).shuffled()

        products = listOf(
            Product(
                id = 1,
                title = "Polo Shirt",
                price = 55.99,
                description = productDescription,
                category = "women's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "Cargo Pants",
                price = 90.00,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Skirt",
                price = 78.78,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Jeans",
                price = 235.99,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 5,
                title = "Shirt",
                price = 85.99,
                description = productDescription,
                category = "women's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 6,
                title = "Blouse",
                price = 99.99,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            )
        ).shuffled()

        cartProducts = listOf(
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
                id = 4,
                title = "Jeans",
                price = 235.99,
                imageUrl = "imageUrl",
                amount = 3
            ),
            CartProduct(
                id = 6,
                title = "Blouse",
                price = 99.99,
                imageUrl = "imageUrl",
                amount = 7
            )
        ).shuffled()
    }

    @Test
    fun `cart products are set correctly`() {
        val resultCartProducts = setUserCartProductsUseCase(cartItems,products)

        assertThat(resultCartProducts).containsExactlyElementsIn(cartProducts)
    }
}