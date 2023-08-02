package com.example.shopapp.presentation.favourites

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.MainDispatcherRule
import com.example.shopapp.util.Resource
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
    private lateinit var productList: List<Product>
    @MockK
    private lateinit var user: FirebaseUser
    private lateinit var userFavourites: List<Favourite>
    private lateinit var favouriteProducts: List<Product>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { shopUseCases.getCurrentUserUseCase() } returns user
        every { user.uid } returns "userUID"

        productList = listOf(
            Product(
                id = 1,
                title = "title 1",
                price = "123,99 PLN",
                description = "description of a product 1",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "title 2",
                price = "41,99 PLN",
                description = "description of a product 2",
                category = "women's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "title 3",
                price = "34,99 PLN",
                description = "description of a product 3",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = false
            )
        )

        userFavourites = listOf(
            Favourite(
                favouriteId = "favourite1",
                productId = 1,
                userUID = "userUID"
            ),
            Favourite(
                favouriteId = "favourite9",
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
                favouriteId = "favourite15",
                productId = 15,
                userUID = "userUID"
            )
        )

        favouriteProducts = listOf(
            Product(
                id = 1,
                title = "title 1",
                price = "123,99 PLN",
                description = "description of a product 1",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = true
            ),
            Product(
                id = 3,
                title = "title 3",
                price = "34,99 PLN",
                description = "description of a product 3",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = true
            )
        )
    }

    @After
    fun tearDown() {
        confirmVerified(shopUseCases)
        clearAllMocks()
    }

    private fun setViewModel(): FavouritesViewModel {
        return FavouritesViewModel(shopUseCases)
    }

    private fun getCurrentFavouriteState(): FavouritesState {
        return favouritesViewModel.favouritesState.value
    }

    @Test
    fun `checkIfUserIsLoggedIn is successful`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(emptyList())
        )

        favouritesViewModel = setViewModel()

        val isUserLoggedIn = getCurrentFavouriteState().isUserLoggedIn

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
        }
        assertThat(isUserLoggedIn).isTrue()
    }

    @Test
    fun `product list is set correctly on init`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(userFavourites)
        )
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(productList)
        )
        every {
            shopUseCases.filterProductsByUserFavouritesUseCase(any(),any())
        } returns favouriteProducts

        favouritesViewModel = setViewModel()

        val favourites = getCurrentFavouriteState().productList

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.filterProductsByUserFavouritesUseCase(any(),any())
        }
        assertThat(favourites).isEqualTo(favouriteProducts)
    }

    @Test
    fun `get user favourites result is successful`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(userFavourites)
        )
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(emptyList())
        )

        favouritesViewModel = setViewModel()

        val favourites = getCurrentFavouriteState().favouriteList
        val products = getCurrentFavouriteState().productList
        val isLoading = getCurrentFavouriteState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
        }
        assertThat(favourites).isEqualTo(userFavourites)
        assertThat(products).isEmpty()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get user favourites result is error`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Error("Error")
        )

        favouritesViewModel = setViewModel()

        val favourites = getCurrentFavouriteState().favouriteList
        val products = getCurrentFavouriteState().productList
        val isLoading = getCurrentFavouriteState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
        }
        assertThat(favourites).isEmpty()
        assertThat(products).isEmpty()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get user favourites result is loading`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Loading(true)
        )

        favouritesViewModel = setViewModel()

        val favourites = getCurrentFavouriteState().favouriteList
        val products = getCurrentFavouriteState().productList
        val isLoading = getCurrentFavouriteState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
        }
        assertThat(favourites).isEmpty()
        assertThat(products).isEmpty()
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `get products result is successful`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(userFavourites)
        )
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(productList)
        )
        every {
            shopUseCases.filterProductsByUserFavouritesUseCase(any(),any())
        } returns favouriteProducts

        favouritesViewModel = setViewModel()

        val favourites = getCurrentFavouriteState().favouriteList
        val products = getCurrentFavouriteState().productList
        val isLoading = getCurrentFavouriteState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.filterProductsByUserFavouritesUseCase(any(),any())
        }
        assertThat(favourites).isEqualTo(userFavourites)
        assertThat(products).isEqualTo(favouriteProducts)
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get products result is error`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(userFavourites)
        )
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Error("Error")
        )

        favouritesViewModel = setViewModel()

        val favourites = getCurrentFavouriteState().favouriteList
        val products = getCurrentFavouriteState().productList
        val isLoading = getCurrentFavouriteState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
        }
        assertThat(favourites).isEqualTo(userFavourites)
        assertThat(products).isEmpty()
        assertThat(isLoading).isFalse()
    }

    @Test
    fun `get products result is loading`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(userFavourites)
        )
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Loading(true)
        )

        favouritesViewModel = setViewModel()

        val favourites = getCurrentFavouriteState().favouriteList
        val products = getCurrentFavouriteState().productList
        val isLoading = getCurrentFavouriteState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
        }
        assertThat(favourites).isEqualTo(userFavourites)
        assertThat(products).isEmpty()
        assertThat(isLoading).isTrue()
    }

    @Test
    fun `event onProductSelected sets product id state correctly`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(userFavourites)
        )
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(productList)
        )
        every {
            shopUseCases.filterProductsByUserFavouritesUseCase(any(),any())
        } returns favouriteProducts

        favouritesViewModel = setViewModel()

        val initialProductId = getCurrentFavouriteState().productId
        favouritesViewModel.onEvent(FavouritesEvent.OnProductSelected(1))
        val resultProductId = getCurrentFavouriteState().productId

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.filterProductsByUserFavouritesUseCase(any(),any())
        }
        assertThat(initialProductId).isEqualTo(-1)
        assertThat(resultProductId).isEqualTo(1)
    }
}