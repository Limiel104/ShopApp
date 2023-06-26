package com.example.shopapp.presentation.category

data class CategoryState (
    val categoryId: String = "",
    val productList: List<String> = emptyList(),
    val isSortSectionVisible: Boolean = false,
    val productId: String = ""
)