package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.CartRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteProductFromCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartItemId: String): Flow<Resource<Boolean>> {
        return cartRepository.deleteProductFromCart(cartItemId)
    }
}