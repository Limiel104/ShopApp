package com.example.shopapp.presentation.favourites

sealed class FavouritesUiEvent {
    data class NavigateToProductDetails(val productId: String): FavouritesUiEvent()
    object NavigateToLogin: FavouritesUiEvent()
    object NavigateToSignup: FavouritesUiEvent()
}