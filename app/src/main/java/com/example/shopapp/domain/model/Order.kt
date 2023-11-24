package com.example.shopapp.domain.model

import java.util.Date

data class Order(
    val orderId: String,
    val date: Date,
    val totalAmount: Double,
    val products: List<CartProduct>,
    val isExpanded: Boolean
) {
    fun totalAmountToString(): String {
        val formattedPrice = String.format("%.2f PLN", totalAmount)
        return formattedPrice.replace(".", ",")
    }
}