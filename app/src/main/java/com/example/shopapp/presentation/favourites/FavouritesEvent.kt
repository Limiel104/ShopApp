package com.example.shopapp.presentation.favourites

sealed class FavouritesEvent {
    data class OnProductSelected(val value: String): FavouritesEvent()
    object OnLogin: FavouritesEvent()
    object OnSignup: FavouritesEvent()
}