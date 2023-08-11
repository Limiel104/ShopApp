package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product
import com.example.shopapp.util.Category
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class FilterProductsUseCaseTest {

    private lateinit var filterProductsUseCase: FilterProductsUseCase
    private lateinit var productList: List<Product>

    @Before
    fun setUp() {
        filterProductsUseCase = FilterProductsUseCase()

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
    fun `products are filtered by category correctly - one category true`() {
        val categoryFilterMap = mapOf(
            Pair(Category.Men.title,true),
            Pair(Category.Women.title,false),
            Pair(Category.Jewelery.title,false),
            Pair(Category.Electronics.title,false)
        )
        val products = filterProductsUseCase(productList, 5.99F,2230.99F,categoryFilterMap)

        assertThat(products.size).isEqualTo(3)
        for(product in products){
            assertThat(product.category).isEqualTo("men's clothing")
        }
    }

    @Test
    fun `products are filtered by category correctly - two categories true`() {
        val categoryFilterMap = mapOf(
            Pair(Category.Men.title,true),
            Pair(Category.Women.title,false),
            Pair(Category.Jewelery.title,true),
            Pair(Category.Electronics.title,false)
        )
        val products = filterProductsUseCase(productList, 5.99F,2230.99F,categoryFilterMap)

        assertThat(products.size).isEqualTo(4)
        for(product in products){
            assertThat(product.category).isAnyOf("men's clothing","jewelery")
        }
    }

    @Test
    fun `products are filtered by category correctly - three categories true`() {
        val categoryFilterMap = mapOf(
            Pair(Category.Men.title,true),
            Pair(Category.Women.title,false),
            Pair(Category.Jewelery.title,true),
            Pair(Category.Electronics.title,true)
        )
        val products = filterProductsUseCase(productList, 5.99F,2230.99F,categoryFilterMap)

        assertThat(products.size).isEqualTo(5)
        for(product in products){
            assertThat(product.category).isAnyOf("men's clothing","jewelery","electronics")
        }
    }

    @Test
    fun `products are filtered by category correctly - all categories true`() {
        val categoryFilterMap = mapOf(
            Pair(Category.Men.title,true),
            Pair(Category.Women.title,true),
            Pair(Category.Jewelery.title,true),
            Pair(Category.Electronics.title,true)
        )
        val products = filterProductsUseCase(productList, 5.99F,2230.99F,categoryFilterMap)

        assertThat(products.size).isEqualTo(7)
        for(product in products){
            assertThat(product.category).isAnyOf("men's clothing","jewelery","electronics","women's clothing")
        }
    }

    @Test
    fun `products are filtered by price correctly - all in`() {
        val minPrice = 5.99F
        val maxPrice = 2230.99F

        val categoryFilterMap = mapOf(
            Pair(Category.Men.title,true),
            Pair(Category.Women.title,true),
            Pair(Category.Jewelery.title,true),
            Pair(Category.Electronics.title,true)
        )
        val products = filterProductsUseCase(productList,minPrice,maxPrice,categoryFilterMap)

        assertThat(products.size).isEqualTo(7)
        for(product in products){
            val price = product.price.replace(",",".").replace("[^0-9.]".toRegex(),"").toFloat()
            assert(price in minPrice..maxPrice)
        }
    }

    @Test
    fun `products are filtered by price correctly - 4 in`() {
        val minPrice = 5.99F
        val maxPrice = 150.99F

        val categoryFilterMap = mapOf(
            Pair(Category.Men.title,true),
            Pair(Category.Women.title,true),
            Pair(Category.Jewelery.title,true),
            Pair(Category.Electronics.title,true)
        )
        val products = filterProductsUseCase(productList,minPrice,maxPrice,categoryFilterMap)

        assertThat(products.size).isEqualTo(4)
        for(product in products){
            val price = product.price.replace(",",".").replace("[^0-9.]".toRegex(),"").toFloat()
            assert(price in minPrice..maxPrice)
        }
    }

    @Test
    fun `products are filtered by price correctly - none in`() {
        val minPrice = 150.99F
        val maxPrice = 150.99F

        val categoryFilterMap = mapOf(
            Pair(Category.Men.title,true),
            Pair(Category.Women.title,true),
            Pair(Category.Jewelery.title,true),
            Pair(Category.Electronics.title,true)
        )
        val products = filterProductsUseCase(productList,minPrice,maxPrice,categoryFilterMap)

        assertThat(products).isEmpty()
    }

    @Test
    fun `products are filtered by price and category correctly - 1 in`() {
        val minPrice = 55.99F
        val maxPrice = 150.99F

        val categoryFilterMap = mapOf(
            Pair(Category.Men.title,true),
            Pair(Category.Women.title,false),
            Pair(Category.Jewelery.title,true),
            Pair(Category.Electronics.title,false)
        )
        val products = filterProductsUseCase(productList,minPrice,maxPrice,categoryFilterMap)

        assertThat(products.size).isEqualTo(1)
        for(product in products){
            val price = product.price.replace(",",".").replace("[^0-9.]".toRegex(),"").toFloat()
            assert(price in minPrice..maxPrice)
        }
    }
}