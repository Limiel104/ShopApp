package com.example.shopapp.data.mapper

import com.example.shopapp.data.local.ProductEntity
import com.example.shopapp.data.remote.ProductDto
import com.example.shopapp.domain.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        imageUrl = imageUrl,
        isInFavourites = false
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        imageUrl = imageUrl,
        isInFavourites = isInFavourites
    )
}

fun Product.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        imageUrl = imageUrl,
        isInFavourites = isInFavourites
    )
}