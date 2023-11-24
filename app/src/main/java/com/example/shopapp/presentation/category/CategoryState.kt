package com.example.shopapp.presentation.category

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.util.ProductOrder

data class CategoryState (
    val categoryId: String = "",
    val productList: List<Product> = emptyList(),
    val isSortAndFilterSectionVisible: Boolean = false,
    val productId: Int = -1,
    val isLoading: Boolean = false,
    val userFavourites: List<Favourite> = emptyList(),
    val userUID: String? = null,
    val isButtonEnabled: Boolean = true,
    val isDialogActivated: Boolean = false,
    val priceSliderRange: ClosedFloatingPointRange<Float> = 0f..0f,
    val priceSliderPosition: ClosedFloatingPointRange<Float> = 0f..0f,
    val isRangeSet: Boolean = false,
    val productOrder: ProductOrder = ProductOrder.NameAscending(),
    val categoryFilterMap: Map<String,Boolean> = mapOf(
        Pair("Men's clothing",true),
        Pair("Women's clothing",true),
        Pair("Jewelery",true),
        Pair("Electronics",true)
    )
)