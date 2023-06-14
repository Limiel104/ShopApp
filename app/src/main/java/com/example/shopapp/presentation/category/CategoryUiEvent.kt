package com.example.shopapp.presentation.category

sealed class CategoryUiEvent {
    data class NavigateToProductDetails(val productId: String): CategoryUiEvent()
}