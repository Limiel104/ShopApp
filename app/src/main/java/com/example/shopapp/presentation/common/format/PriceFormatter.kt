package com.example.shopapp.presentation.common.format

import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Product

fun CartProduct.priceToString(): String {
    val formattedPrice = String.format("%.2f PLN", price)
    return formattedPrice.replace(".", ",")
}


fun Product.priceToString(): String {
    val formattedPrice = String.format("%.2f PLN", price)
    return formattedPrice.replace(".", ",")
}