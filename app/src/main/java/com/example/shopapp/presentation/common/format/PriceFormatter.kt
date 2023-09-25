package com.example.shopapp.presentation.common.format

import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.model.Product

fun CartProduct.priceToString(): String {
    val formattedPrice = String.format("%.2f PLN", price)
    return formattedPrice.replace(".", ",")
}


fun Product.priceToString(): String {
    val formattedPrice = String.format("%.2f PLN", price)
    return formattedPrice.replace(".", ",")
}

fun Order.priceToString(): String {
    val formattedPrice = String.format("%.2f PLN", totalAmount)
    return formattedPrice.replace(".", ",")
}