package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.domain.util.Resource

class GetProductUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: Int): Resource<Product> {
        return productRepository.getProduct(productId)
    }
}