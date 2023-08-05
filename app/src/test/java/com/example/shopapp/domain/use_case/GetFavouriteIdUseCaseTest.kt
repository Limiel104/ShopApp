package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Favourite
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class GetFavouriteIdUseCaseTest {

    private lateinit var getFavouriteIdUseCase: GetFavouriteIdUseCase
    private lateinit var favourites: List<Favourite>

    @Before
    fun setUp() {
        getFavouriteIdUseCase = GetFavouriteIdUseCase()

        favourites = listOf(
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
                favouriteId = "favourite8",
                productId = 3,
                userUID = "userUID"
            ),
            Favourite(
                favouriteId = "favourite12",
                productId = 1,
                userUID = "userUID"
            ),
            Favourite(
                favouriteId = "favourite17",
                productId = 15,
                userUID = "userUID"
            )
        )
    }

    @Test
    fun `get favourite's id`() {
        val productId = 1
        val favouriteId = "favourite12"

        val result = getFavouriteIdUseCase(favourites, productId)

        assertThat(favouriteId).isEqualTo(result)
    }
}