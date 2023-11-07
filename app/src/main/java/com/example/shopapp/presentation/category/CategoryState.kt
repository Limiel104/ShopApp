package com.example.shopapp.presentation.category

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.util.ProductOrder
import com.example.shopapp.util.Category

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
        Pair(Category.Men.title,true),
        Pair(Category.Women.title,true),
        Pair(Category.Jewelery.title,true),
        Pair(Category.Electronics.title,true)
    )
)