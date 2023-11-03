package com.example.shopapp.presentation.home

import com.example.shopapp.domain.model.Banner

data class HomeState (
    val bannerList: List<Banner> = emptyList()
)