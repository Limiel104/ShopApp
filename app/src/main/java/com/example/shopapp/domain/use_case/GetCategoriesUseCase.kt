package com.example.shopapp.domain.use_case

import android.util.Log
import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Constants.all
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class GetCategoriesUseCase(
    private val productRepository: ProductRepository
) {
    operator fun invoke(): Flow<List<String>> = flow {
        try {
            val categoryList: MutableList<String> = mutableListOf()
            categoryList.addAll(productRepository.getCategories())
            categoryList.add(all)
            emit(categoryList)
        }
        catch (e: IOException) {
            Log.i(TAG,e.message.toString())
            emit(emptyList())
        }
    }
}