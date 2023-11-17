package com.example.shopapp.presentation.category_list

import com.example.shopapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.impl.annotations.MockK
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
    private lateinit var categoryListViewModel: CategoryListViewModel
    private lateinit var categoryListIds: List<String>
    private lateinit var categoryListTitles: List<String>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        categoryListIds = listOf(
            "all",
            "men's clothing",
            "women's clothing",
            "jewelery",
            "electronics"
        )

        categoryListTitles = listOf(
            "All",
            "Men's clothing",
            "Women's clothing",
            "Jewelery",
            "Electronics"
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    private fun setViewModel(): CategoryListViewModel {
        return CategoryListViewModel()
    }

    private fun getCurrentCategoryListState(): CategoryListState {
        return categoryListViewModel.categoryListState.value
    }

    @Test
    fun `getCategories returns category list`() {
        categoryListViewModel = setViewModel()
        categoryListViewModel.getCategories()
        val categories = getCurrentCategoryListState().categoryList

        assertThat(categories).isEqualTo(categoryListTitles)
    }

    @Test
    fun `categoryList is of correct size when getCategoriesUseCase returns correct list`() {
        categoryListViewModel = setViewModel()
        val categories = getCurrentCategoryListState().categoryList

        assertThat(categories).isNotEmpty()
        assertThat(categories.size).isEqualTo(5)
        assertThat(categories).isEqualTo(categoryListTitles)
    }

    @Test
    fun `event onCategorySelected sets categoryId correctly`() {
        categoryListViewModel = setViewModel()
        val initialId = getCurrentCategoryListState().categoryId
        categoryListViewModel.onEvent(CategoryListEvent.OnCategorySelected("men's clothing"))
        val resultId = getCurrentCategoryListState().categoryId

        assertThat(initialId).isEqualTo("")
        assertThat(resultId).isEqualTo("men's clothing")
        assertThat(categoryListIds).contains(resultId)
    }
}