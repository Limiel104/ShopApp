package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.Product
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(): Flow<Resource<List<Product>>>

    suspend fun getProductsFromCategory(categoryId: String): Flow<Resource<List<Product>>>

    suspend fun getProduct(productId: Int): Resource<Product>

    suspend fun getCategories(): Flow<Resource<List<String>>>
}