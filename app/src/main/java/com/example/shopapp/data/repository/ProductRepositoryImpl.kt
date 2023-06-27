package com.example.shopapp.data.repository

import com.example.shopapp.data.mapper.toProduct
import com.example.shopapp.data.remote.FakeShopApi
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: FakeShopApi
): ProductRepository {

    override suspend fun getProducts(): List<Product> {
        return api.getProducts().map { productDto ->
            productDto.toProduct()
        }
    }
}