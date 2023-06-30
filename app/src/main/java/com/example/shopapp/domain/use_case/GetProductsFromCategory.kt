package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetProductsFromCategory(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(categoryId: String): Flow<Resource<List<Product>>> {
        return productRepository.getProductsFromCategory(categoryId)
//        try {
//            val products = productRepository.getProductsFromCategory(categoryId)
//            emit(products)
//        }
//        catch (e: IOException) {
//            Log.i(Constants.TAG,e.message.toString())
//            emit(emptyList())
//        }
    }
}