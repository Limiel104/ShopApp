package com.example.shopapp.presentation.category

import androidx.lifecycle.SavedStateHandle
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

class CategoryViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    @MockK
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var productList: List<Product>
    @MockK
    private lateinit var user: FirebaseUser
    private lateinit var userFavourites: List<Favourite>
    private lateinit var products: List<Product>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { savedStateHandle.get<String>(any()) } returns "men's clothing"
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

        products = listOf(
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
                isInFavourites = true
            )
        )
    }

    @After
    fun tearDown() {
        confirmVerified(shopUseCases)
        clearAllMocks()
    }

    private fun setViewModel(): CategoryViewModel {
        return CategoryViewModel(savedStateHandle, shopUseCases)
    }

    private fun getCurrentCategoryState(): CategoryState {
        return categoryViewModel.categoryState.value
    }

    @Test
    fun `category id is set correctly on init`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(emptyList())
        )
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(emptyList())
        )
        every {
            shopUseCases.setUserFavouritesUseCase(any(),any())
        } returns emptyList()

        categoryViewModel = setViewModel()

        val categoryId = getCurrentCategoryState().categoryId
        assertThat(categoryId).isEqualTo("men's clothing")

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
        }
    }

    @Test
    fun `get products result is successful`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(emptyList())
        )
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(productList)
        )
        every {
            shopUseCases.setUserFavouritesUseCase(any(),any())
        } returns productList

        categoryViewModel = setViewModel()

        val products = getCurrentCategoryState().productList
        assertThat(products).isEqualTo(productList)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
        }
    }

    @Test
    fun `get products result is error`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(emptyList())
        )
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Error("Error")
        )

        categoryViewModel = setViewModel()

        val products = getCurrentCategoryState().productList
        assertThat(products).isEmpty()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
        }
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

        categoryViewModel = setViewModel()

        val products = getCurrentCategoryState().productList
        val loadingState = getCurrentCategoryState().isLoading

        assertThat(products).isEmpty()
        assertThat(loadingState).isTrue()
        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
        }
    }

    @Test
    fun `add favourite result is successful`() {
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
            shopUseCases.setUserFavouritesUseCase(any(), any())
        } returns products
        coEvery {
            shopUseCases.addProductToFavouritesUseCase(any(), any())
        } returns flowOf(
            Resource.Success(true)
        )

        categoryViewModel = setViewModel()

        categoryViewModel.addProductToUserFavourites(1, "userUID")
        val loadingState = getCurrentCategoryState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.addProductToFavouritesUseCase(any(),any())
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `add favourite result is loading`() {
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
            shopUseCases.setUserFavouritesUseCase(any(), any())
        } returns products
        coEvery {
            shopUseCases.addProductToFavouritesUseCase(any(), any())
        } returns flowOf(
            Resource.Loading(true)
        )

        categoryViewModel = setViewModel()

        categoryViewModel.addProductToUserFavourites(1, "userUID")
        val loadingState = getCurrentCategoryState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.addProductToFavouritesUseCase(any(),any())
        }
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `delete favourite result is successful`() {
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
            shopUseCases.setUserFavouritesUseCase(any(), any())
        } returns products
        coEvery {
            shopUseCases.deleteProductFromFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(true)
        )

        categoryViewModel = setViewModel()

        categoryViewModel.deleteProductFromUserFavourites("favouriteId")
        val loadingState = getCurrentCategoryState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.deleteProductFromFavouritesUseCase(any())
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `delete favourite result is loading`() {
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
            shopUseCases.setUserFavouritesUseCase(any(), any())
        } returns products
        coEvery {
            shopUseCases.deleteProductFromFavouritesUseCase(any())
        } returns flowOf(
            Resource.Loading(true)
        )

        categoryViewModel = setViewModel()

        categoryViewModel.deleteProductFromUserFavourites("favouriteId")
        val loadingState = getCurrentCategoryState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.deleteProductFromFavouritesUseCase(any())
        }
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `isProductInFavourites returns true`() {
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
            shopUseCases.setUserFavouritesUseCase(any(), any())
        } returns products

        categoryViewModel = setViewModel()

        val result = categoryViewModel.isProductInFavourites(1)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
        }
        assertThat(result).isTrue()
    }

    @Test
    fun `isProductInFavourites returns false`() {
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
            shopUseCases.setUserFavouritesUseCase(any(), any())
        } returns products

        categoryViewModel = setViewModel()

        val result = categoryViewModel.isProductInFavourites(2)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
        }
        assertThat(result).isFalse()
    }

    @Test
    fun `set user favourites sets product list properly`() {
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
            shopUseCases.setUserFavouritesUseCase(any(), any())
        } returns products

        categoryViewModel = setViewModel()

        val result = getCurrentCategoryState()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
        }
        assertThat(result.productList).isNotEmpty()
        assertThat(result.productList).isEqualTo(products)
    }

    @Test
    fun `set user favourites leaves product list the same`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(emptyList())
        )
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(productList)
        )
        every {
            shopUseCases.setUserFavouritesUseCase(any(), any())
        } returns productList

        categoryViewModel = setViewModel()

        val result = getCurrentCategoryState()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
        }
        assertThat(result.productList).isNotEmpty()
        assertThat(result.productList).isEqualTo(productList)
    }

    @Test
    fun `event onProductSelected sets state with selected product`() {
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
            shopUseCases.setUserFavouritesUseCase(any(),any())
        } returns products

        categoryViewModel = setViewModel()

        val initialProductId = getCurrentCategoryState().productId
        assertThat(initialProductId).isEqualTo(-1)

        categoryViewModel.onEvent(CategoryEvent.OnProductSelected(1))

        val resultProductId = getCurrentCategoryState().productId
        assertThat(resultProductId).isEqualTo(1)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
        }
    }

    @Test
    fun `event toggleSortSection reverses sort section state `() {
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
            shopUseCases.setUserFavouritesUseCase(any(),any())
        } returns products

        categoryViewModel = setViewModel()

        val initialToggleState = getCurrentCategoryState().isSortSectionVisible
        assertThat(initialToggleState).isEqualTo(false)

        categoryViewModel.onEvent(CategoryEvent.ToggleSortSection)

        val resultToggleState = getCurrentCategoryState().isSortSectionVisible
        assertThat(resultToggleState).isEqualTo(true)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
        }
    }

    @Test
    fun `event toggleSortSection reverses sort section state 6 times`() {
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
            shopUseCases.setUserFavouritesUseCase(any(),any())
        } returns products

        categoryViewModel = setViewModel()

        val initialToggleState = getCurrentCategoryState().isSortSectionVisible
        assertThat(initialToggleState).isEqualTo(false)

        categoryViewModel.onEvent(CategoryEvent.ToggleSortSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortSection)

        val firstCheckOfToggleState = getCurrentCategoryState().isSortSectionVisible
        assertThat(firstCheckOfToggleState).isEqualTo(true)

        categoryViewModel.onEvent(CategoryEvent.ToggleSortSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortSection)

        val secondCheckOfToggleState = getCurrentCategoryState().isSortSectionVisible
        assertThat(secondCheckOfToggleState).isEqualTo(false)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
        }
    }

    @Test
    fun `event onFavouriteButtonSelected delete when product is in user favourites`() {
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
            shopUseCases.setUserFavouritesUseCase(any(),any())
        } returns products
        coEvery {
            shopUseCases.getFavouriteIdUseCase(userFavourites,1)
        } returns "favourite1"
        coEvery {
            shopUseCases.deleteProductFromFavouritesUseCase(any())
        } returns flowOf(
            Resource.Success(true)
        )

        categoryViewModel = setViewModel()

        categoryViewModel.onEvent(CategoryEvent.OnFavouriteButtonSelected(1))

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.getFavouriteIdUseCase(any(),any())
            shopUseCases.deleteProductFromFavouritesUseCase(any())
        }
    }

    @Test
    fun `event onFavouriteButtonSelected delete when product is not in user favourites`() {
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
            shopUseCases.setUserFavouritesUseCase(any(),any())
        } returns products
        coEvery {
            shopUseCases.addProductToFavouritesUseCase(any(),any())
        } returns flowOf(
            Resource.Success(true)
        )

        categoryViewModel = setViewModel()

        categoryViewModel.onEvent(CategoryEvent.OnFavouriteButtonSelected(2))

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.addProductToFavouritesUseCase(any(),any())
        }
    }
}