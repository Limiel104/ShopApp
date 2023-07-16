package com.example.shopapp.presentation.favourites

sealed class FavouriteEvent {
    data class OnProductSelected(val value: String): FavouriteEvent()
    object OnLogin: FavouriteEvent()
    object OnSignup: FavouriteEvent()
}