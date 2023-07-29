package com.example.shopapp.presentation.category

sealed class CategoryEvent {
    data class OnProductSelected(val value: Int): CategoryEvent()
    data class OnFavouriteButtonSelected(val value: Int): CategoryEvent()
    object ToggleSortSection: CategoryEvent()
}