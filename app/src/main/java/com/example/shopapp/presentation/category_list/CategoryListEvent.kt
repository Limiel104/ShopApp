package com.example.shopapp.presentation.category_list

sealed class CategoryListEvent {
    data class OnCategorySelected(val value: String): CategoryListEvent()
    object GoToCart: CategoryListEvent()
}