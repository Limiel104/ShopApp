package com.example.shopapp.domain.model

import java.util.Date

data class FirebaseOrder(
    val orderId: String = "",
    val userUID: String = "",
    val date: Date = Date(),
    val totalAmount: Double = -1.0,
    val products: Map<String,Int> = emptyMap()
)