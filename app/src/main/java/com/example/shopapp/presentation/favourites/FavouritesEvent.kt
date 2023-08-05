package com.example.shopapp.presentation.favourites

sealed class FavouritesEvent {
    data class OnProductSelected(val value: Int): FavouritesEvent()
    object OnLogin: FavouritesEvent()
    object OnSignup: FavouritesEvent()
    data class OnDelete(val value: Int): FavouritesEvent()
}