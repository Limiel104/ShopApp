package com.example.shopapp.domain.model

data class Offer(
    val categoryId: String = "all",
    val discountPercent: Int,
    val description: String
)