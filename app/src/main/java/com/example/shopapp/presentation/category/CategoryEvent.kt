package com.example.shopapp.presentation.category

sealed class CategoryEvent {
    data class OnProductSelected(val value: String): CategoryEvent()
}