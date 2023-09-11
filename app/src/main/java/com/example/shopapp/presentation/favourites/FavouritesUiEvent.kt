package com.example.shopapp.presentation.favourites

sealed class FavouritesUiEvent {
    data class NavigateToProductDetails(val productId: Int): FavouritesUiEvent()
    object NavigateToLogin: FavouritesUiEvent()
    object NavigateToSignup: FavouritesUiEvent()
    data class ShowErrorMessage(val message: String): FavouritesUiEvent()
    object NavigateToCart: FavouritesUiEvent()
}