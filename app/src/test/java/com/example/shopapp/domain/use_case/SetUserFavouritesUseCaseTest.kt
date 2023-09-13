package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class SetUserFavouritesUseCaseTest {

    private lateinit var setUserFavouritesUseCase: SetUserFavouritesUseCase
    private lateinit var productList: List<Product>
    private lateinit var userFavourites: List<Favourite>

    @Before
    fun setUp() {
        setUserFavouritesUseCase = SetUserFavouritesUseCase()

        productList = listOf(
            Product(
                id = 1,
                title = "Shirt",
                price = 195.59,
                description = Constants.productDescription,
                category = Category.Women.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "Shirt",
                price = 195.59,
                description = Constants.productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Shirt",
                price = 195.59,
                description = Constants.productDescription,
                category = Category.Jewelery.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Shirt",
                price = 195.59,
                description = Constants.productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            )
        )

        userFavourites = listOf(
            Favourite(
                favouriteId = "favourite1",
                productId = 9,
                userUID = "userUID"
            ),
            Favourite(
                favouriteId = "favourite7",
                productId = 7,
                userUID = "userUID"
            ),
            Favourite(
                favouriteId = "favourite3",
                productId = 3,
                userUID = "userUID"
            ),
            Favourite(
                favouriteId = "favourite1",
                productId = 1,
                userUID = "userUID"
            ),
            Favourite(
                favouriteId = "favourite15",
                productId = 15,
                userUID = "userUID"
            )
        )
    }

    @Test
    fun `set isInFavourites Product property properly`() {
        val products = listOf(
            Product(
                id = 1,
                title = "Shirt",
                price = 195.59,
                description = Constants.productDescription,
                category = Category.Women.id,
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 2,
                title = "Shirt",
                price = 195.59,
                description = Constants.productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Shirt",
                price = 195.59,
                description = Constants.productDescription,
                category = Category.Jewelery.id,
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 4,
                title = "Shirt",
                price = 195.59,
                description = Constants.productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            )
        )

        val result = setUserFavouritesUseCase(productList,userFavourites)

        assertThat(result).isEqualTo(products)
    }

    @Test
    fun `when userFavourites is empty the return products list stays the same`() {
        val favourites = emptyList<Favourite>()

        val result = setUserFavouritesUseCase(productList,favourites)

        assertThat(result).isNotEmpty()
        assertThat(result).isEqualTo(productList)
        assertThat(result).isSameInstanceAs(productList)
    }

    @Test
    fun `when productList is empty the return products list empty`() {
        val products = emptyList<Product>()

        val result = setUserFavouritesUseCase(products,userFavourites)

        assertThat(result).isEmpty()
        assertThat(result).isEqualTo(products)
        assertThat(result).isSameInstanceAs(products)
    }

    @Test
    fun `when productsList and userFavourites are empty the return products list is empty`() {
        val products = emptyList<Product>()
        val favourites = emptyList<Favourite>()

        val result = setUserFavouritesUseCase(products,favourites)

        assertThat(result).isEmpty()
        assertThat(result).isEqualTo(products)
        assertThat(result).isSameInstanceAs(products)
    }

    @Test
    fun `when all products are in user favourites all are set correctly`() {
        val products = productList.filter { product ->
            product.id % 2 == 1
        }

        val result = setUserFavouritesUseCase(products,userFavourites)

        assertThat(products.size).isEqualTo(2)
        assertThat(result).isNotEmpty()
        assertThat(result.size).isEqualTo(2)
        for (res in result) {
            assertThat(res.isInFavourites).isTrue()
        }
    }

    @Test
    fun `when none of the products are in user favourites the isInFavourites property is set to false`() {
        val products = productList.filter { product ->
            product.id % 2 == 0
        }

        val result = setUserFavouritesUseCase(products,userFavourites)

        assertThat(products.size).isEqualTo(2)
        assertThat(result).isNotEmpty()
        assertThat(result.size).isEqualTo(2)
        for (res in result) {
            assertThat(res.isInFavourites).isFalse()
        }
    }
}