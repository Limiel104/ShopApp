package com.example.shopapp.presentation.category

import androidx.lifecycle.SavedStateHandle
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.MainDispatcherRule
import com.example.shopapp.util.Resource
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
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

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { savedStateHandle.get<String>(any()) } returns "men's clothing"

        productList = listOf(
            Product(
                id = 1,
                title = "title 1",
                price = "123,99 PLN",
                description = "description of a product 1",
                category = "men's clothing",
                imageUrl = "url"
            ),
            Product(
                id = 2,
                title = "title 2",
                price = "41,99 PLN",
                description = "description of a product 2",
                category = "women's clothing",
                imageUrl = "url"
            ),
            Product(
                id = 3,
                title = "title 3",
                price = "34,99 PLN",
                description = "description of a product 3",
                category = "men's clothing",
                imageUrl = "url"
            )
        )
    }

    @After
    fun tearDown() {
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
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(productList)
        )

        categoryViewModel = setViewModel()

        val categoryId = getCurrentCategoryState().categoryId
        assertThat(categoryId).isEqualTo("men's clothing")

        coVerify(exactly = 1) { shopUseCases.getProductsUseCase(any()) }
    }

    @Test
    fun `get products result is success`() {
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(productList)
        )

        categoryViewModel = setViewModel()

        val products = getCurrentCategoryState().productList
        assertThat(products).isEqualTo(productList)

        coVerify(exactly = 1) { shopUseCases.getProductsUseCase(any()) }
    }

    @Test
    fun `get products result is error`() {
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Error("Error")
        )

        categoryViewModel = setViewModel()

        val products = getCurrentCategoryState().productList
        assertThat(products).isEmpty()

        coVerify(exactly = 1) { shopUseCases.getProductsUseCase(any()) }
    }

    @Test
    fun `get products result is loading`() {
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
        coVerify(exactly = 1) { shopUseCases.getProductsUseCase(any()) }
    }

    @Test
    fun `event onProductSelected sets state with selected product`() {
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(productList)
        )

        categoryViewModel = setViewModel()

        val initialProductId = getCurrentCategoryState().productId
        assertThat(initialProductId).isEqualTo(-1)

        categoryViewModel.onEvent(CategoryEvent.OnProductSelected(0))

        val resultProductId = getCurrentCategoryState().productId
        assertThat(resultProductId).isEqualTo(0)

        coVerify(exactly = 1) { shopUseCases.getProductsUseCase(any()) }
    }

    @Test
    fun `event toggleSortSection reverses sort section state `() {
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(productList)
        )

        categoryViewModel = setViewModel()

        val initialToggleState = getCurrentCategoryState().isSortSectionVisible
        assertThat(initialToggleState).isEqualTo(false)

        categoryViewModel.onEvent(CategoryEvent.ToggleSortSection)

        val resultToggleState = getCurrentCategoryState().isSortSectionVisible
        assertThat(resultToggleState).isEqualTo(true)

        coVerify(exactly = 1) { shopUseCases.getProductsUseCase(any()) }
    }

    @Test
    fun `event toggleSortSection reverses sort section state 6 times`() {
        coEvery {
            shopUseCases.getProductsUseCase(any())
        } returns flowOf(
            Resource.Success(productList)
        )

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

        coVerify(exactly = 1) { shopUseCases.getProductsUseCase(any()) }
    }
}