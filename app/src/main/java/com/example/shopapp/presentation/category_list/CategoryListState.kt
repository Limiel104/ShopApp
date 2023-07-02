package com.example.shopapp.presentation.category_list

import com.example.shopapp.util.Category

data class CategoryListState (
    val categoryId: String = "",
    val categoryList: List<Category> = emptyList()
)