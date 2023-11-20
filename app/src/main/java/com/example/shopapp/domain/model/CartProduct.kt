package com.example.shopapp.domain.model

data class CartProduct(
    val id: Int,
    val title: String,
    val price: Double,
    val imageUrl: String,
    val amount: Int
) {
    fun priceToString(): String {
        val formattedPrice = String.format("%.2f PLN", price)
        return formattedPrice.replace(".", ",")
    }
}