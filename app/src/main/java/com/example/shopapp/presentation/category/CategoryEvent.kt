package com.example.shopapp.presentation.category

import com.example.shopapp.domain.util.ProductOrder

sealed class CategoryEvent {
    data class OnProductSelected(val value: Int): CategoryEvent()
    data class OnFavouriteButtonSelected(val value: Int): CategoryEvent()
    data class OnPriceSliderPositionChange(val value: ClosedFloatingPointRange<Float>): CategoryEvent()
    data class OnOrderChange(val value: ProductOrder): CategoryEvent()
    object ToggleSortSection: CategoryEvent()
    object OnDialogDismissed: CategoryEvent()
}