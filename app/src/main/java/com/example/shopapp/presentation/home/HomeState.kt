package com.example.shopapp.presentation.home

import com.example.shopapp.domain.model.Offer

data class HomeState (
    val offerList: List<Offer> = emptyList()
)