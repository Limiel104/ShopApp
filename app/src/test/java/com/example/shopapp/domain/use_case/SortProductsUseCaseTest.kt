package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.util.ProductOrder
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test

class SortProductsUseCaseTest {

    private lateinit var sortProductsUseCase: SortProductsUseCase
    private lateinit var productList: List<Product>

    @Before
    fun setUp() {
        sortProductsUseCase = SortProductsUseCase()

        productList = listOf(
            Product(
                id = 1,
                title = "Shirt",
                price = "70,99 PLN",
                description = "description of a product 1",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "Pants",
                price = "120,99 PLN",
                description = "description of a product 2",
                category = "women's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Socks",
                price = "5,99 PLN",
                description = "description of a product 3",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Jacket",
                price = "178,99 PLN",
                description = "description of a product 3",
                category = "women's clothing",
                imageUrl = "url",
                isInFavourites = false
            )
            ,
            Product(
                id = 5,
                title = "TV",
                price = "2230,99 PLN",
                description = "description of a product 3",
                category = "electronics",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 6,
                title = "Hat",
                price = "13,99 PLN",
                description = "description of a product 3",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = false
            )
            ,
            Product(
                id = 7,
                title = "Necklace",
                price = "1000,00 PLN",
                description = "description of a product 3",
                category = "jewelery",
                imageUrl = "url",
                isInFavourites = false
            )
        )
    }

    @Test
    fun `products are sorted by name asc correctly`() {
        val products = sortProductsUseCase(ProductOrder.NameAscending(),productList)

        for(i in 0..products.size-2) {
            assertThat(products[i].title.lowercase()).isLessThan(products[i+1].title.lowercase())
        }
    }

    @Test
    fun `products are sorted by name desc correctly`() {
        val products = sortProductsUseCase(ProductOrder.NameDescending(),productList)

        for(i in 0..products.size-2) {
            assertThat(products[i].title.lowercase()).isGreaterThan(products[i+1].title.lowercase())
        }
    }

    @Test
    fun `products are sorted by price asc correctly`() {
        val products = sortProductsUseCase(ProductOrder.PriceAscending(),productList)

        for(i in 0..products.size-2) {
            val currentProductPrice =  products[i].price.replace(",",".").replace("[^0-9.]".toRegex(),"").toFloat()
            val nextProductPrice = products[i+1].price.replace(",",".").replace("[^0-9.]".toRegex(),"").toFloat()
            assertThat(currentProductPrice).isLessThan(nextProductPrice)
        }
    }

    @Test
    fun `products are sorted by price desc correctly`() {
        val products = sortProductsUseCase(ProductOrder.PriceDescending(),productList)

        for(i in 0..products.size-2) {
            val currentProductPrice =  products[i].price.replace(",",".").replace("[^0-9.]".toRegex(),"").toFloat()
            val nextProductPrice = products[i+1].price.replace(",",".").replace("[^0-9.]".toRegex(),"").toFloat()
            assertThat(currentProductPrice).isGreaterThan(nextProductPrice)
        }
    }
}