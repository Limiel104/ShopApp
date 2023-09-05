package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants.productDescription
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class FilterProductsByUserFavouritesUseCaseTest {

    private lateinit var filterProductsByUserFavouritesUseCase: FilterProductsByUserFavouritesUseCase
    private lateinit var products: List<Product>
    private lateinit var favourites: List<Favourite>
    private lateinit var filteredProducts: List<Product>

    @Before
    fun setUp() {
        filterProductsByUserFavouritesUseCase = FilterProductsByUserFavouritesUseCase()

        products = listOf(
            Product(
                id = 1,
                title = "Polo Shirt",
                price = "55,99 PLN",
                description = productDescription,
                category = Category.Women.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "Cargo Pants",
                price = "90,00 PLN",
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Skirt",
                price = "78,78 PLN",
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Jeans",
                price = "235,99 PLN",
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 5,
                title = "Shirt",
                price = "85,99 PLN",
                description = productDescription,
                category = Category.Women.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 6,
                title = "Blouse",
                price = "99,99 PLN",
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = false
            )
        ).shuffled()

        favourites = listOf(
            Favourite(
                favouriteId = "favouriteId2",
                userUID = "userUID",
                productId = 2
            ),
            Favourite(
                favouriteId = "favouriteId5",
                userUID = "userUID",
                productId = 5
            ),
            Favourite(
                favouriteId = "favouriteId6",
                userUID = "userUID",
                productId = 6
            )
        ).shuffled()

        filteredProducts = listOf(
            Product(
                id = 2,
                title = "Cargo Pants",
                price = "90,00 PLN",
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 5,
                title = "Shirt",
                price = "85,99 PLN",
                description = productDescription,
                category = Category.Women.id,
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 6,
                title = "Blouse",
                price = "99,99 PLN",
                description = productDescription,
                category = Category.Men.id,
                imageUrl = "imageUrl",
                isInFavourites = true
            )
        ).shuffled()
    }

    @Test
    fun `favourite products are set correctly`() {
        val resultFavouriteProducts = filterProductsByUserFavouritesUseCase(products,favourites)

        assertThat(resultFavouriteProducts).containsExactlyElementsIn(filteredProducts)
    }
}