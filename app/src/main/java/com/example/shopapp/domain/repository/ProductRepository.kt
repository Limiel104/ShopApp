package com.example.shopapp.domain.repository

import com.example.shopapp.data.remote.ProductDto

interface ProductRepository {

    suspend fun getProducts(): List<ProductDto>

    suspend fun getProductsFromCategory(categoryId: String): List<ProductDto>

    suspend fun getProduct(productId: Int): ProductDto

    suspend fun getCategories(): List<String>
}