package com.example.shopapp.presentation.category

import androidx.lifecycle.SavedStateHandle
import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.domain.util.ProductOrder
import com.example.shopapp.util.MainDispatcherRule
import com.example.shopapp.domain.util.Resource
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
    private lateinit var filteredProducts: List<Product>
    private lateinit var sortedProducts: List<Product>
    private lateinit var categoryFilterMap: Map<String,Boolean>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { savedStateHandle.get<String>(any()) } returns "men's clothing"
        every { shopUseCases.getCurrentUserUseCase() } returns user
        every { user.uid } returns "userUID"

        productList = listOf(
            Product(
                id = 3,
                title = "title 3",
                price = 123.99,
                description = "description of a product 3",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "title 4",
                price = 41.99,
                description = "description of a product 4",
                category = "women's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 1,
                title = "title 1",
                price = 34.99,
                description = "description of a product 1",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 2,
                title = "title 2",
                price = 66.99,
                description = "description of a product 2",
                category = "women's clothing",
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
                id = 3,
                title = "title 3",
                price = 123.99,
                description = "description of a product 3",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = true
            ),
            Product(
                id = 4,
                title = "title 4",
                price = 41.99,
                description = "description of a product 4",
                category = "women's clothing",
                imageUrl = "url",
                isInFavourites = false
            ),
            Product(
                id = 1,
                title = "title 1",
                price = 34.99,
                description = "description of a product 1",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = true
            ),
            Product(
                id = 2,
                title = "title 2",
                price = 66.99,
                description = "description of a product 2",
                category = "women's clothing",
                imageUrl = "url",
                isInFavourites = false
            )
        )

        filteredProducts = listOf(
            Product(
                id = 3,
                title = "title 3",
                price = 123.99,
                description = "description of a product 3",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = true
            ),
            Product(
                id = 1,
                title = "title 1",
                price = 34.99,
                description = "description of a product 1",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = true
            )
        )

        sortedProducts = listOf(
            Product(
                id = 1,
                title = "title 1",
                price = 34.99,
                description = "description of a product 1",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = true
            ),
            Product(
                id = 3,
                title = "title 3",
                price = 123.99,
                description = "description of a product 3",
                category = "men's clothing",
                imageUrl = "url",
                isInFavourites = true
            )
        )

        categoryFilterMap = mapOf(
            Pair("Men's clothing",true),
            Pair("Women's clothing",false),
            Pair("Jewelery",false),
            Pair("Electronics",false)
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
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(emptyList()))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(emptyList()))

        categoryViewModel = setViewModel()

        val categoryId = getCurrentCategoryState().categoryId

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase(any())
            shopUseCases.getProductsUseCase(any())
        }
        assertThat(categoryId).isEqualTo("men's clothing")
    }

    @Test
    fun `get products result is successful`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(emptyList()))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList, emptyList())
        } returns productList
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns productList
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns productList

        categoryViewModel = setViewModel()
        val products = getCurrentCategoryState().productList

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(any(),any())
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),any())
        }
        assertThat(products).isEqualTo(productList)
    }

    @Test
    fun `get products result is error`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(emptyList()))
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(Resource.Error("Error"))

        categoryViewModel = setViewModel()
        val products = getCurrentCategoryState().productList

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
        }
        assertThat(products).isEmpty()
    }

    @Test
    fun `get products result is loading`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Loading(true))

        categoryViewModel = setViewModel()
        val products = getCurrentCategoryState().productList
        val loadingState = getCurrentCategoryState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
        }
        assertThat(products).isEmpty()
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `add favourite result is successful`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts
        coEvery {
            shopUseCases.addProductToFavouritesUseCase(1,"userUID")
        } returns flowOf(Resource.Success(true))

        categoryViewModel = setViewModel()
        categoryViewModel.addProductToUserFavourites(1, "userUID")
        val loadingState = getCurrentCategoryState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList, userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
            shopUseCases.addProductToFavouritesUseCase(1,"userUID")
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `add favourite result is loading`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts
        coEvery {
            shopUseCases.addProductToFavouritesUseCase(1,"userUID")
        } returns flowOf(Resource.Loading(true))

        categoryViewModel = setViewModel()
        categoryViewModel.addProductToUserFavourites(1, "userUID")
        val loadingState = getCurrentCategoryState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
            shopUseCases.addProductToFavouritesUseCase(1,"userUID")
        }
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `delete favourite result is successful`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts
        coEvery {
            shopUseCases.deleteProductFromFavouritesUseCase("favouriteId")
        } returns flowOf(Resource.Success(true))

        categoryViewModel = setViewModel()
        categoryViewModel.deleteProductFromUserFavourites("favouriteId")
        val loadingState = getCurrentCategoryState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
            shopUseCases.deleteProductFromFavouritesUseCase("favouriteId")
        }
        assertThat(loadingState).isFalse()
    }

    @Test
    fun `delete favourite result is loading`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts
        coEvery {
            shopUseCases.deleteProductFromFavouritesUseCase("favouriteId")
        } returns flowOf(Resource.Loading(true))

        categoryViewModel = setViewModel()
        categoryViewModel.deleteProductFromUserFavourites("favouriteId")
        val loadingState = getCurrentCategoryState().isLoading

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
            shopUseCases.deleteProductFromFavouritesUseCase("favouriteId")
        }
        assertThat(loadingState).isTrue()
    }

    @Test
    fun `isProductInFavourites returns true`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts

        categoryViewModel = setViewModel()
        val result = categoryViewModel.isProductInFavourites(1)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        }
        assertThat(result).isTrue()
    }

    @Test
    fun `isProductInFavourites returns false`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts

        categoryViewModel = setViewModel()
        val result = categoryViewModel.isProductInFavourites(2)

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        }
        assertThat(result).isFalse()
    }

    @Test
    fun `set user favourites sets product list properly`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns products
        every {
            shopUseCases.sortProductsUseCase(any(),products)
        } returns products

        categoryViewModel = setViewModel()
        val result = getCurrentCategoryState()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),products)
        }
        assertThat(result.productList).isNotEmpty()
        assertThat(result.productList).isEqualTo(products)
    }

    @Test
    fun `set user favourites leaves product list the same`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(emptyList()))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList, emptyList())
        } returns productList
        every {
            shopUseCases.filterProductsUseCase(productList,any(),any(),any())
        } returns productList
        every {
            shopUseCases.sortProductsUseCase(any(),productList)
        } returns productList

        categoryViewModel = setViewModel()
        val result = getCurrentCategoryState()

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,any())
            shopUseCases.filterProductsUseCase(productList,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),productList)
        }
        assertThat(result.productList).isNotEmpty()
        assertThat(result.productList).isEqualTo(productList)
    }

    @Test
    fun `changeButtonLockState sets button lock state correctly`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(emptyList()))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(emptyList()))

        categoryViewModel = setViewModel()
        val initialSate = getCurrentCategoryState().isButtonEnabled
        categoryViewModel.isFavouriteButtonEnabled(!initialSate)
        val resultState = getCurrentCategoryState().isButtonEnabled

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
        }
        assertThat(initialSate).isTrue()
        assertThat(resultState).isFalse()
    }

    @Test
    fun `price slider is set correctly`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts

        categoryViewModel = setViewModel()
        val sliderPosition = getCurrentCategoryState().priceSliderPosition
        val sliderRange = getCurrentCategoryState().priceSliderRange

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        }
        assertThat(sliderPosition).isEqualTo(34.99F..123.99F)
        assertThat(sliderRange).isEqualTo(34.99F..123.99F)
    }

    @Test
    fun `products are filtered correctly`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(any(),any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns filteredProducts

        categoryViewModel = setViewModel()
        categoryViewModel.filterProducts()
        val filteredProducts = getCurrentCategoryState().productList

        excludeRecords {
            shopUseCases.sortProductsUseCase(any(),any())
        }

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.filterProductsUseCase(filteredProducts,any(),any(),any())
        }
        for(product in filteredProducts) {
            assertThat(product.category).isEqualTo("men's clothing")
        }
    }

    @Test
    fun `products are sorted correctly`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts

        categoryViewModel = setViewModel()
        val sortedProducts = getCurrentCategoryState().productList

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        }
        for(i in 0..sortedProducts.size-2) {
            assertThat(sortedProducts[i].title).isLessThan(sortedProducts[i+1].title)
        }
    }

    @Test
    fun `toggle checkbox works correctly`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts
        every {
            shopUseCases.toggleCheckBoxUseCase(any(),"Men's clothing")
        } returns categoryFilterMap

        categoryViewModel = setViewModel()
        val initialMapState = getCurrentCategoryState().categoryFilterMap
        categoryViewModel.toggleCheckBox("Men's clothing")
        val resultMapState = getCurrentCategoryState().categoryFilterMap

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
            shopUseCases.toggleCheckBoxUseCase(any(),"Men's clothing")
        }
        for(category in initialMapState.values) {
            assertThat(category).isTrue()
        }
        assertThat(resultMapState).isEqualTo(categoryFilterMap)
    }

    @Test
    fun `event toggleSortSection reverses sort section state `() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts

        categoryViewModel = setViewModel()
        val initialToggleState = getCurrentCategoryState().isSortAndFilterSectionVisible
        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)
        val resultToggleState = getCurrentCategoryState().isSortAndFilterSectionVisible

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        }
        assertThat(initialToggleState).isEqualTo(false)
        assertThat(resultToggleState).isEqualTo(true)
    }

    @Test
    fun `event toggleSortSection reverses sort section state 6 times`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts

        categoryViewModel = setViewModel()
        val initialToggleState = getCurrentCategoryState().isSortAndFilterSectionVisible
        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)
        val firstCheckOfToggleState = getCurrentCategoryState().isSortAndFilterSectionVisible
        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)
        categoryViewModel.onEvent(CategoryEvent.ToggleSortAndFilterSection)
        val secondCheckOfToggleState = getCurrentCategoryState().isSortAndFilterSectionVisible

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        }
        assertThat(initialToggleState).isEqualTo(false)
        assertThat(firstCheckOfToggleState).isEqualTo(true)
        assertThat(secondCheckOfToggleState).isEqualTo(false)
    }

    @Test
    fun `event onFavouriteButtonSelected delete when product is in user favourites`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts
        coEvery {
            shopUseCases.getFavouriteIdUseCase(userFavourites,1)
        } returns "favourite1"
        coEvery {
            shopUseCases.deleteProductFromFavouritesUseCase("favourite1")
        } returns flowOf(Resource.Success(true))

        categoryViewModel = setViewModel()
        categoryViewModel.onEvent(CategoryEvent.OnFavouriteButtonSelected(1))

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
            shopUseCases.getFavouriteIdUseCase(userFavourites,1)
            shopUseCases.deleteProductFromFavouritesUseCase("favourite1")
        }
    }

    @Test
    fun `event onFavouriteButtonSelected delete when product is not in user favourites`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts
        coEvery {
            shopUseCases.addProductToFavouritesUseCase(2,"userUID")
        } returns flowOf(Resource.Success(true))

        categoryViewModel = setViewModel()
        categoryViewModel.onEvent(CategoryEvent.OnFavouriteButtonSelected(2))

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
            shopUseCases.addProductToFavouritesUseCase(2,"userUID")
        }
    }

    @Test
    fun `event onFavouriteButtonSelected sets button lock state correctly when it runs`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts
        coEvery {
            shopUseCases.addProductToFavouritesUseCase(2,"userUID")
        } returns flowOf(Resource.Success(true))

        categoryViewModel = setViewModel()
        val initialState = getCurrentCategoryState().isButtonEnabled
        categoryViewModel.onEvent(CategoryEvent.OnFavouriteButtonSelected(2))
        val resultState = getCurrentCategoryState().isButtonEnabled

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
            shopUseCases.addProductToFavouritesUseCase(2,"userUID")
        }
        assertThat(initialState).isTrue()
        assertThat(resultState).isTrue()
    }

    @Test
    fun `event onDialogDismissed sets is dialog activated state correctly`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts

        categoryViewModel = setViewModel()
        val initialState = getCurrentCategoryState().isDialogActivated
        categoryViewModel.onEvent(CategoryEvent.OnDialogDismissed)
        val resultState = getCurrentCategoryState().isDialogActivated

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        }
        assertThat(initialState).isFalse()
        assertThat(resultState).isFalse()
    }

    @Test
    fun `event onPriceSliderPositionChange is setting new position of the slider correctly`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts

        categoryViewModel = setViewModel()
        val initialSliderPosition = getCurrentCategoryState().priceSliderPosition
        categoryViewModel.onEvent(CategoryEvent.OnPriceSliderPositionChange(50.07F..100.45F))
        val resultSliderPosition = getCurrentCategoryState().priceSliderPosition

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        }
        assertThat(initialSliderPosition).isEqualTo(34.99F..123.99F)
        assertThat(resultSliderPosition).isEqualTo(50.07F..100.45F)
    }

    @Test
    fun `event onOrderChange is setting new product order correctly`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),any())
        } returns sortedProducts

        categoryViewModel = setViewModel()
        categoryViewModel.onEvent(CategoryEvent.OnOrderChange(ProductOrder.NameAscending()))
        val sortedProducts = getCurrentCategoryState().productList

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
            shopUseCases.sortProductsUseCase(any(),sortedProducts)
        }
        for(i in 0..sortedProducts.size-2) {
            assertThat(sortedProducts[i].title).isLessThan(sortedProducts[i+1].title)
        }
    }

    @Test
    fun `event onCheckBoxToggled is setting new state of categoryFilterMap correctly`() {
        coEvery {
            shopUseCases.getUserFavouritesUseCase("userUID")
        } returns flowOf(Resource.Success(userFavourites))
        coEvery {
            shopUseCases.getProductsUseCase("men's clothing")
        } returns flowOf(Resource.Success(productList))
        every {
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
        } returns products
        every {
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
        } returns filteredProducts
        every {
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        } returns sortedProducts
        every {
            shopUseCases.toggleCheckBoxUseCase(any(),"Men's clothing")
        } returns categoryFilterMap

        categoryViewModel = setViewModel()

        val initialMapState = getCurrentCategoryState().categoryFilterMap
        categoryViewModel.onEvent(CategoryEvent.OnCheckBoxToggled("Men's clothing"))
        val resultMapState = getCurrentCategoryState().categoryFilterMap

        coVerifySequence {
            shopUseCases.getCurrentUserUseCase()
            shopUseCases.getUserFavouritesUseCase("userUID")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
            shopUseCases.toggleCheckBoxUseCase(any(), "Men's clothing")
            shopUseCases.getProductsUseCase("men's clothing")
            shopUseCases.setUserFavouritesUseCase(productList,userFavourites)
            shopUseCases.filterProductsUseCase(products,any(),any(),any())
            shopUseCases.sortProductsUseCase(any(),filteredProducts)
        }
        for(category in initialMapState.values) {
            assertThat(category).isTrue()
        }

        assertThat(resultMapState).isEqualTo(categoryFilterMap)
    }
}