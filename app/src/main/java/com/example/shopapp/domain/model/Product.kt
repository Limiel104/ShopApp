package com.example.shopapp.domain.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val imageUrl: String,
    val isInFavourites: Boolean
) {
    fun priceToString(): String {
        val formattedPrice = String.format("%.2f PLN", price)
        return formattedPrice.replace(".", ",")
    }
}