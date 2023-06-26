package com.example.shopapp.presentation.category

import com.example.shopapp.domain.model.Product

data class CategoryState (
    val categoryId: String = "",
    val productList: List<Product> = emptyList(),
    val isSortSectionVisible: Boolean = false,
    val productId: String = ""
)