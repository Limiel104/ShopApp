package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Product>>> {
        return productRepository.getProducts()
//        try {
//            val products = productRepository.getProducts()
//            emit(products)
//        }
//        catch (e: IOException) {
//            Log.i(TAG,e.message.toString())
//            emit(emptyList())
//        }
    }
}