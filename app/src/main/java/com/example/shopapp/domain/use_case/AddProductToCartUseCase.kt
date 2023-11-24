package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.CartRepository
import com.example.shopapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class AddProductToCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(
        userUID: String,
        productId: Int,
        amount: Int
    ): Flow<Resource<Boolean>> {
        return cartRepository.addProductToCart(userUID,productId,amount)
    }
}