package com.example.shopapp.domain.use_case

import com.example.shopapp.util.Category
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class GetCategoriesUseCaseTest {

    private lateinit var getCategoriesUseCase: GetCategoriesUseCase

    @Before
    fun setUp() {
        getCategoriesUseCase = GetCategoriesUseCase()
    }

    @Test
    fun `category list is returned correctly`() {
        val categoryList = getCategoriesUseCase()
        val correctCategoryList = listOf(
            Category.All,
            Category.Men,
            Category.Women,
            Category.Jewelery,
            Category.Electronics
        )

        assertThat(categoryList).isEqualTo(correctCategoryList)
    }

    @Test
    fun `category list is of correct size`() {
        val categoryListSize = getCategoriesUseCase().size
        assertThat(categoryListSize).isEqualTo(5)
    }
}