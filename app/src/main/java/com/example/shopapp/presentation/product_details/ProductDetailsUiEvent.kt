package com.example.shopapp.presentation.product_details

sealed class ProductDetailsUiEvent {
    object NavigateBack: ProductDetailsUiEvent()
    data class ShowErrorMessage(val message: String): ProductDetailsUiEvent()
    object ShowProductAddedToCartMessage: ProductDetailsUiEvent()
}