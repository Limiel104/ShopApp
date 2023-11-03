package com.example.shopapp.presentation.home

sealed class HomeEvent {
    data class OnBannerSelected(val value: String): HomeEvent()
    object GoToCart: HomeEvent()
}