package com.example.shopapp.presentation.category_list

import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Category
import com.example.shopapp.util.MainDispatcherRule
import com.example.shopapp.util.getCategory
import com.google.common.truth.Truth.assertThat
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

class CategoryListViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var shopUseCases: ShopUseCases
    private lateinit var categoryListViewModel: CategoryListViewModel
    private lateinit var categoryList: List<Category>
    private lateinit var categoryIdList: List<String>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        categoryList = listOf(
            Category.All,
            Category.Men,
            Category.Women,
            Category.Jewelery,
            Category.Electronics
        )

        categoryIdList = listOf(
            Category.All.categoryId,
            Category.Men.categoryId,
            Category.Women.categoryId,
            Category.Jewelery.categoryId,
            Category.Electronics.categoryId
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun setViewModel(
        state: CategoryListState = CategoryListState("", emptyList())
    ): CategoryListViewModel {
        val viewModel = CategoryListViewModel(shopUseCases)
        viewModel.categoryListState.value.copy(
            categoryId = state.categoryId,
            categoryList = state.categoryList
        )
        return viewModel
    }

    private fun getCurrentCategoryListState(): CategoryListState {
        return categoryListViewModel.categoryListState.value
    }

    @Test
    fun `categoryList remains empty when getCategoriesUseCase returns empty list`() {
       every { shopUseCases.getCategoriesUseCase() } returns emptyList()

        categoryListViewModel = setViewModel()

        val categories= getCurrentCategoryListState().categoryList
        assertThat(categories).isEmpty()

        verify { shopUseCases.getCategoriesUseCase() }
    }

    @Test
    fun `categoryList is of correct size when getCategoriesUseCase returns correct list`() {
        every { shopUseCases.getCategoriesUseCase() } returns getCategory()

        categoryListViewModel = setViewModel()

        val categories = getCurrentCategoryListState().categoryList
        assertThat(categories).isNotEmpty()
        assertThat(categories.size).isEqualTo(5)
        assertThat(categories).isEqualTo(categoryList)

        verify { shopUseCases.getCategoriesUseCase() }
    }

    @Test
    fun `event onCategorySelected sets categoryId correctly`() {
        every { shopUseCases.getCategoriesUseCase() } returns getCategory()

        categoryListViewModel = setViewModel()

        val initialId = getCurrentCategoryListState().categoryId
        assertThat(initialId).isEqualTo("")

        categoryListViewModel.onEvent(CategoryListEvent.OnCategorySelected("men's clothing"))

        val resultId = getCurrentCategoryListState().categoryId
        assertThat(resultId).isEqualTo("men's clothing")
        assertThat(categoryIdList).contains(resultId)

        verify { shopUseCases.getCategoriesUseCase() }
    }
}