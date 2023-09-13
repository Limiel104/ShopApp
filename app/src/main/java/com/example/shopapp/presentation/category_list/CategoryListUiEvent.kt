package com.example.shopapp.presentation.category_list

sealed class CategoryListUiEvent {
    data class NavigateToCategory(val categoryId: String): CategoryListUiEvent()
    object NavigateToCart: CategoryListUiEvent()
}