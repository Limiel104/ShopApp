package com.example.shopapp.presentation.favourites

sealed class FavouriteUiEvent {
    data class NavigateToProductDetails(val productId: String): FavouriteUiEvent()
    object NavigateToLogin: FavouriteUiEvent()
    object NavigateToSignup: FavouriteUiEvent()
}