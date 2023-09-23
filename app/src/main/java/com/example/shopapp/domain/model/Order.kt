package com.example.shopapp.domain.model

import java.util.Date

data class Order(
    val orderId: String,
    val date: Date,
    val totalAmount: Double,
    val products: List<CartProduct>,
    val isExpanded: Boolean
)