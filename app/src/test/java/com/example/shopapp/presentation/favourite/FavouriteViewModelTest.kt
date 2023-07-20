package com.example.shopapp.presentation.favourite

import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavouriteViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    private lateinit var favouriteViewModel: FavouriteViewModel
    private lateinit var productList: List<String>
    @MockK
    private lateinit var user: FirebaseUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

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

    private fun setViewModel(): FavouriteViewModel {
        return FavouriteViewModel(shopUseCases)
    }

    private fun getCurrentFavouriteState(): FavouriteState {
        return favouriteViewModel.favouriteState.value
    }

    @Test
    fun `product list is set correctly on init`() {
        every { shopUseCases.getCurrentUserUseCase() } returns user

        favouriteViewModel = setViewModel()

        val favourites = getCurrentFavouriteState().productList
        assertThat(favourites).isEqualTo(productList)
    }

    @Test
    fun `event onProductSelected sets product id state correctly`() {
        every { shopUseCases.getCurrentUserUseCase() } returns user

        favouriteViewModel = setViewModel()

        val initialProductId = getCurrentFavouriteState().productId
        assertThat(initialProductId).isEqualTo("")

        favouriteViewModel.onEvent(FavouriteEvent.OnProductSelected("men's clothing 2"))

        val resultProductId = getCurrentFavouriteState().productId
        assertThat(resultProductId).isEqualTo("men's clothing 2")
    }
}