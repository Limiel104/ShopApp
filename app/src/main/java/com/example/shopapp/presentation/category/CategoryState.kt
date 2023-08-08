package com.example.shopapp.presentation.category

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product

data class CategoryState (
    val categoryId: String = "",
    val productList: List<Product> = emptyList(),
    val isSortSectionVisible: Boolean = false,
    val productId: Int = -1,
    val isLoading: Boolean = false,
    val userFavourites: List<Favourite> = emptyList(),
    val userUID: String? = null,
    val isButtonLocked: Boolean = false,
    val isDialogActivated: Boolean = false,
    val priceSliderRange: ClosedFloatingPointRange<Float> = 0f..0f,
    val priceSliderPosition: ClosedFloatingPointRange<Float> = 0f..0f
)