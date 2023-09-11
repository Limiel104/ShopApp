package com.example.shopapp.presentation.category

sealed class CategoryUiEvent {
    data class NavigateToProductDetails(val productId: Int): CategoryUiEvent()
    data class ShowErrorMessage(val message: String): CategoryUiEvent()
    object NavigateToCart: CategoryUiEvent()
}