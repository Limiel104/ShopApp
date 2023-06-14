package com.example.shopapp.presentation.product_details

sealed class ProductDetailsEvent {
    object GoBack: ProductDetailsEvent()
}