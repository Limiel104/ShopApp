package com.example.shopapp.presentation.favourite

import com.example.shopapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavouriteViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var favouriteViewModel: FavouriteViewModel
    private lateinit var productList: List<String>

    @Before
    fun setUp() {
        favouriteViewModel = FavouriteViewModel()

        productList = listOf(
            "men's clothing 1",
            "men's clothing 2",
            "women's clothing 1",
            "jewelery 1",
            "men's clothing 3",
            "women's clothing 2",
            "jewelery 2",
            "women's clothing 3"
        )
    }

    private fun getCurrentFavouriteState(): FavouriteState {
        return favouriteViewModel.favouriteState.value
    }

    @Test
    fun `product list is set correctly on init`() {
        val favourites = getCurrentFavouriteState().productList
        assertThat(favourites).isEqualTo(productList)
    }

    @Test
    fun `event onProductSelected sets product id state correctly`() {
        val initialProductId = getCurrentFavouriteState().productId
        assertThat(initialProductId).isEqualTo("")

        favouriteViewModel.onEvent(FavouriteEvent.OnProductSelected("men's clothing 2"))

        val resultProductId = getCurrentFavouriteState().productId
        assertThat(resultProductId).isEqualTo("men's clothing 2")
    }
}