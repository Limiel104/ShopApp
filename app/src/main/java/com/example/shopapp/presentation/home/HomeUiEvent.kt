package com.example.shopapp.presentation.home

sealed class HomeUiEvent {
    data class NavigateToCategory(val categoryId: String): HomeUiEvent()
    object NavigateToCart: HomeUiEvent()
}