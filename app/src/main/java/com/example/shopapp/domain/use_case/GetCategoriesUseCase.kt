package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.ProductRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetCategoriesUseCase(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<String>>> {
        return productRepository.getCategories()
//        try {
////            val categoryList: MutableList<String> = mutableListOf()
////            val categories = productRepository.getCategories().collect {
////
////            }
////            categoryList.addAll()
////            categoryList.add(all)
////            emit(categoryList)
//        }
//        catch (e: IOException) {
//            Log.i(TAG,e.message.toString())
//            emit(emptyList())
//        }
    }
}