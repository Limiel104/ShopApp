package com.example.shopapp.presentation.product_details

sealed class ProductDetailsEvent {
    object OnFavouriteButtonSelected: ProductDetailsEvent()
    object OnAddToCart: ProductDetailsEvent()
    object GoToCart: ProductDetailsEvent()
    object GoBack: ProductDetailsEvent()
}