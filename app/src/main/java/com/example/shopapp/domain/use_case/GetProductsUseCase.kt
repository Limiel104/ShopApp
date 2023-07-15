package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(categoryId: String): Flow<Resource<List<Product>>> {
        return productRepository.getProducts(categoryId)
    }
}