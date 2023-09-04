package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.repository.CartRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetUserCartItemUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(userUID: String, productId: Int): Flow<Resource<List<CartItem>>> {
        return cartRepository.getUserCartItem(userUID,productId)
    }
}