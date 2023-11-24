package com.example.shopapp.presentation.product_details

sealed class ProductDetailsUiEvent {
    data class ShowErrorMessage(val message: String): ProductDetailsUiEvent()
    object ShowProductAddedToCartMessage: ProductDetailsUiEvent()
    object NavigateToCart: ProductDetailsUiEvent()
    object NavigateBack: ProductDetailsUiEvent()
}