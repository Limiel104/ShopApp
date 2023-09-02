package com.example.shopapp.presentation.home

sealed class HomeEvent {
    data class OnOfferSelected(val value: String): HomeEvent()
    object GoToCart: HomeEvent()
}