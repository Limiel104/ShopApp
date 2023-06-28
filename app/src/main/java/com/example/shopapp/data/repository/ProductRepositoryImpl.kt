package com.example.shopapp.data.repository

import com.example.shopapp.data.remote.FakeShopApi
import com.example.shopapp.data.remote.ProductDto
import com.example.shopapp.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: FakeShopApi
): ProductRepository {

    override suspend fun getProducts(): List<ProductDto> {
        return api.getProducts()
    }

    override suspend fun getProductsFromCategory(categoryId: String): List<ProductDto> {
        return api.getProductsFromCategory(categoryId)
    }

    override suspend fun getProduct(productId: Int): ProductDto {
        return api.getProduct(productId)
    }

    override suspend fun getCategories(): List<String> {
        return api.getCategories()
    }
}