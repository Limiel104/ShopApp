package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(categoryId: String): Flow<Resource<List<Product>>>

    suspend fun getProduct(productId: Int): Resource<Product>
}