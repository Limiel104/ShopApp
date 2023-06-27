package com.example.shopapp.domain.use_case

import android.util.Log
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.util.Constants.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetProductsUseCase(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<List<Product>> = flow {
        try {
            emit(productRepository.getProducts())
        }
        catch (e: IOException) {
            Log.i(TAG,e.message.toString())
            emit(emptyList())
        }
    }
}