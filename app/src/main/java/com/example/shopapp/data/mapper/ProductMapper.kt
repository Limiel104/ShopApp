package com.example.shopapp.data.mapper

import com.example.shopapp.data.local.ProductEntity
import com.example.shopapp.data.remote.ProductDto
import com.example.shopapp.domain.model.Product

fun ProductDto.toProduct(): Product {
    val formattedPrice = String.format("%.2f PLN", price)
    val mappedPrice = formattedPrice.replace(".",",")

    return Product(
        id = id,
        title = title,
        price = mappedPrice,
        description = description,
        category = category,
        imageUrl = imageUrl,
        isInFavourites = false
    )
}

fun ProductEntity.toProduct(): Product {
    val formattedPrice = String.format("%.2f PLN", price)
    val mappedPrice = formattedPrice.replace(".",",")

    return Product(
        id = id,
        title = title,
        price = mappedPrice,
        description = description,
        category = category,
        imageUrl = imageUrl,
        isInFavourites = isInFavourites
    )
}

fun Product.toProductEntity(): ProductEntity {
    val replacedPrice = price.replace(",",".")
    val priceOnlyDigits = replacedPrice.subSequence(0,replacedPrice.length-4).toString()

    return ProductEntity(
        id = id,
        title = title,
        price = priceOnlyDigits.toDouble(),
        description = description,
        category = category,
        imageUrl = imageUrl,
        isInFavourites = isInFavourites
    )
}