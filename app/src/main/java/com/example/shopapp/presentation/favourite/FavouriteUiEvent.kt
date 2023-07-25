package com.example.shopapp.presentation.favourite

sealed class FavouriteUiEvent {
    data class NavigateToProductDetails(val productId: String): FavouriteUiEvent()
    object NavigateToLogin: FavouriteUiEvent()
    object NavigateToSignup: FavouriteUiEvent()
}