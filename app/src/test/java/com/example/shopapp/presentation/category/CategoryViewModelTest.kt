package com.example.shopapp.presentation.category

import androidx.lifecycle.SavedStateHandle
import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.domain.util.ProductOrder
import com.example.shopapp.util.Category
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
import io.mockk.excludeRecords
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
    private lateinit var categoryFilterMap: Map<String,Boolean>

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
                price = 123.99,
                description = "description of a product 1",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "title 2",
                price = 41.99,
                description = "description of a product 2",
                category = "women's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "title 3",
                price = 34.99,
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
                price = 123.99,
                description = "description of a product 1",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = true
            ),
            Product(
                id = 2,
                title = "title 2",
                price = 41.99,
                description = "description of a product 2",
                category = "women's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "title 3",
                price = 34.99,
                description = "description of a product 3",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = true
            )
        )

        categoryFilterMap = mapOf(
            Pair(Category.Men.title,true),
            Pair(Category.Women.title,false),
            Pair(Category.Jewelery.title,false),
            Pair(Category.Electronics.title,false)
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

        categoryViewModel = setViewModel()

        val categoryId = getCurrentCategoryState().categoryId
        assertThat(categoryId).isEqualTo("men's clothing")

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns productList
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns productList

        categoryViewModel = setViewModel()

        val products = getCurrentCategoryState().productList
        assertThat(products).isEqualTo(productList)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }
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
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }
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
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }
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
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }
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
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }

        categoryViewModel = setViewModel()

        val result = categoryViewModel.isProductInFavourites(1)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }

        categoryViewModel = setViewModel()

        val result = categoryViewModel.isProductInFavourites(2)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }

        categoryViewModel = setViewModel()

        val result = getCurrentCategoryState()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns productList
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns productList

        categoryViewModel = setViewModel()

        val result = getCurrentCategoryState()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
        }
        assertThat(result.productList).isNotEmpty()
        assertThat(result.productList).isEqualTo(productList)
    }

    @Test
    fun `changeButtonLockState sets button lock state correctly`() {
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

        categoryViewModel = setViewModel()

        val initialSate = getCurrentCategoryState().isButtonLocked
        categoryViewModel.changeButtonLockState(!initialSate)
        val resultState = getCurrentCategoryState().isButtonLocked

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
        }
        assertThat(initialSate).isFalse()
        assertThat(resultState).isTrue()
    }

    @Test
    fun `price slider is set correctly`() {
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == "men's clothing" }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title.lowercase() }

        categoryViewModel = setViewModel()
        val sliderPosition = getCurrentCategoryState().priceSliderPosition
        val sliderRange = getCurrentCategoryState().priceSliderRange

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
        }
        assertThat(sliderPosition).isEqualTo(34.99F..123.99F)
        assertThat(sliderRange).isEqualTo(34.99F..123.99F)
    }

    @Test
    fun `products are filtered correctly`() {
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == "men's clothing" }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title.lowercase() }

        categoryViewModel = setViewModel()

        categoryViewModel.filterProducts()
        val filteredProducts = getCurrentCategoryState().productList

        excludeRecords {
            shopUseCases.sortProductsUseCase(any(),any())
        }

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        }
        for(product in filteredProducts) {
            assertThat(product.category).isEqualTo("men's clothing")
        }
    }

    @Test
    fun `products are sorted correctly`() {
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } answers { products.filter { it.category == "men's clothing" } }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title.lowercase() }

        categoryViewModel = setViewModel()

        val sortedProducts = getCurrentCategoryState().productList

        excludeRecords {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        }

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
        }
        for(i in 0..sortedProducts.size-2) {
            assertThat(sortedProducts[i].title).isLessThan(sortedProducts[i+1].title)
        }
    }

    @Test
    fun `toggle checkbox works correctly`() {
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } answers { products.filter { it.category == "men's clothing" } }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title.lowercase() }
        every {
            shopUseCases.toggleCheckBoxUseCase(any(),any())
        } returns mapOf(
            Pair(Category.Men.title,false),
            Pair(Category.Women.title,true),
            Pair(Category.Jewelery.title,true),
            Pair(Category.Electronics.title,true)
        )

        categoryViewModel = setViewModel()

        val initialMapState = getCurrentCategoryState().categoryFilterMap
        categoryViewModel.toggleCheckBox("men's clothing")
        val resultMapState = getCurrentCategoryState().categoryFilterMap

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
            shopUseCases.toggleCheckBoxUseCase(any(),any())
        }
        for(category in initialMapState.values) {
            assertThat(category).isTrue()
        }

        assertThat(resultMapState).isEqualTo(
            mapOf(
                Pair(Category.Men.title,false),
                Pair(Category.Women.title,true),
                Pair(Category.Jewelery.title,true),
                Pair(Category.Electronics.title,true)
            )
        )
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }

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
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }

        categoryViewModel = setViewModel()

        val initialToggleState = getCurrentCategoryState().isSortAndFilterSectionVisible
        assertThat(initialToggleState).isEqualTo(false)

        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)

        val resultToggleState = getCurrentCategoryState().isSortAndFilterSectionVisible
        assertThat(resultToggleState).isEqualTo(true)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }

        categoryViewModel = setViewModel()

        val initialToggleState = getCurrentCategoryState().isSortAndFilterSectionVisible
        assertThat(initialToggleState).isEqualTo(false)

        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)

        val firstCheckOfToggleState = getCurrentCategoryState().isSortAndFilterSectionVisible
        assertThat(firstCheckOfToggleState).isEqualTo(true)

        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)

        val secondCheckOfToggleState = getCurrentCategoryState().isSortAndFilterSectionVisible
        assertThat(secondCheckOfToggleState).isEqualTo(false)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }
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
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }
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
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
            shopUseCases.addProductToFavouritesUseCase(any(),any())
        }
    }

    @Test
    fun `event onFavouriteButtonSelected sets button lock state correctly when it runs`() {
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }
        coEvery {
            shopUseCases.addProductToFavouritesUseCase(any(),any())
        } returns flowOf(
            Resource.Success(true)
        )

        categoryViewModel = setViewModel()

        val initialState = getCurrentCategoryState().isButtonLocked
        categoryViewModel.onEvent(CategoryEvent.OnFavouriteButtonSelected(2))
        val resultState = getCurrentCategoryState().isButtonLocked

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
            shopUseCases.addProductToFavouritesUseCase(any(),any())
        }
        assertThat(initialState).isFalse()
        assertThat(resultState).isFalse()
    }

    @Test
    fun `event onDialogDismissed sets is dialog activated state correctly`() {
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }

        categoryViewModel = setViewModel()

        val initialState = getCurrentCategoryState().isDialogActivated
        categoryViewModel.onEvent(CategoryEvent.OnDialogDismissed)
        val resultState = getCurrentCategoryState().isDialogActivated

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
        }
        assertThat(initialState).isFalse()
        assertThat(resultState).isFalse()
    }

    @Test
    fun `event onPriceSliderPositionChange is setting new position of the slider correctly`() {
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }

        categoryViewModel = setViewModel()

        val initialSliderPosition = getCurrentCategoryState().priceSliderPosition
        categoryViewModel.onEvent(CategoryEvent.OnPriceSliderPositionChange(50.07F..100.45F))
        val resultSliderPosition = getCurrentCategoryState().priceSliderPosition

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
        }
        assertThat(initialSliderPosition).isEqualTo(34.99F..123.99F)
        assertThat(resultSliderPosition).isEqualTo(50.07F..100.45F)
    }

    @Test
    fun `event onOrderChange is setting new product order correctly`() {
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }

        categoryViewModel = setViewModel()

        categoryViewModel.onEvent(CategoryEvent.OnOrderChange(ProductOrder.NameAscending()))
        val sortedProducts = getCurrentCategoryState().productList

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
        }
        for(i in 0..sortedProducts.size-2) {
            assertThat(sortedProducts[i].title).isLessThan(sortedProducts[i+1].title)
        }
    }

    @Test
    fun `event onCheckBoxToggled is setting new state of categoryFilterMap correctly`() {
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
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns products.filter { it.category == Category.Men.id }
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns products.sortedBy { it.title }
        every {
            shopUseCases.toggleCheckBoxUseCase(any(),any())
        } returns mapOf(
            Pair(Category.Men.title,false),
            Pair(Category.Women.title,true),
            Pair(Category.Jewelery.title,true),
            Pair(Category.Electronics.title,true)
        )

        categoryViewModel = setViewModel()

        val initialMapState = getCurrentCategoryState().categoryFilterMap
        categoryViewModel.onEvent(CategoryEvent.OnCheckBoxToggled("men's clothing"))
        val resultMapState = getCurrentCategoryState().categoryFilterMap

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
            shopUseCases.toggleCheckBoxUseCase(any(),any())
            shopUseCases.getProductsUseCase(any())
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
        }
        for(category in initialMapState.values) {
            assertThat(category).isTrue()
        }

        assertThat(resultMapState).isEqualTo(
            mapOf(
                Pair(Category.Men.title,false),
                Pair(Category.Women.title,true),
                Pair(Category.Jewelery.title,true),
                Pair(Category.Electronics.title,true)
            )
        )
    }
}