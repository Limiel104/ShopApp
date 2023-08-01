package com.example.shopapp.presentation.favourites

import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavouritesViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    private lateinit var favouritesViewModel: FavouritesViewModel
    private lateinit var productList: List<String>
    @MockK
    private lateinit var user: FirebaseUser

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { shopUseCases.getCurrentUserUseCase() } returns user
        this.favouritesViewModel = FavouritesViewModel(shopUseCases)

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

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun getCurrentFavouriteState(): FavouritesState {
        return favouritesViewModel.favouritesState.value
    }

    @Test
    fun `checkIfUserIsLoggedIn is successful`() {
        favouritesViewModel.checkIfUserIsLoggedIn()

        val isUserLoggedIn = favouritesViewModel.favouritesState.value.isUserLoggedIn

        assertThat(isUserLoggedIn).isTrue()
        verify(exactly = 2) { shopUseCases.getCurrentUserUseCase() }
    }

    @Test
    fun `product list is set correctly on init`() {
        val favourites = getCurrentFavouriteState().productList

        assertThat(favourites).isEqualTo(productList)
        verify(exactly = 1) { shopUseCases.getCurrentUserUseCase() }
    }

    @Test
    fun `event onProductSelected sets product id state correctly`() {
        val initialProductId = getCurrentFavouriteState().productId

        favouritesViewModel.onEvent(FavouritesEvent.OnProductSelected("men's clothing 2"))

        val resultProductId = getCurrentFavouriteState().productId

        assertThat(initialProductId).isEqualTo("")
        assertThat(resultProductId).isEqualTo("men's clothing 2")
        verify(exactly = 1) { shopUseCases.getCurrentUserUseCase() }
    }
}