package com.example.shopapp.data.mapper

import com.example.shopapp.data.remote.ProductDto
import com.example.shopapp.domain.model.Product

fun ProductDto.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        imageUrl = imageUrl
    )
}