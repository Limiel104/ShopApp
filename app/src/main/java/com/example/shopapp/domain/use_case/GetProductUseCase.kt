package com.example.shopapp.domain.use_case

import android.util.Log
import com.example.shopapp.data.mapper.toProduct
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetProductUseCase(
    private val productRepository: ProductRepository
) {
    operator fun invoke(productId: Int): Flow<Product> = flow {
        try {
            val product = productRepository.getProduct(productId).toProduct()
            emit(product)
        }
        catch (e: IOException) {
            Log.i(Constants.TAG,e.message.toString())
        }
    }
}