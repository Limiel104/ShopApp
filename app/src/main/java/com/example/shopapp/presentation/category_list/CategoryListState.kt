package com.example.shopapp.presentation.category_list

data class CategoryListState (
    val categoryId: String = "",
    val categoryList: List<String> = emptyList()
)