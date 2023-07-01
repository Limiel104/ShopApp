package com.example.shopapp.data.mapper

import com.example.shopapp.data.local.ProductEntity
import com.example.shopapp.data.remote.ProductDto
import com.example.shopapp.domain.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = String.format("%.2f PLN", price),
        description = description,
        category = category,
        imageUrl = imageUrl
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = String.format("%.2f PLN", price),
        description = description,
        category = category,
        imageUrl = imageUrl
    )
}

fun Product.toProductEntity(): ProductEntity {
    val priceOnlyDigits = price.subSequence(0,price.length-4).toString()

    return ProductEntity(
        id = id,
        title = title,
        price = priceOnlyDigits.toDouble(),
        description = description,
        category = category,
        imageUrl = imageUrl
    )
}