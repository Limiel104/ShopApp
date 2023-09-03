package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.repository.CartRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetUserCartItemsUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(userUID: String): Flow<Resource<List<CartItem>>> {
        return cartRepository.getCartItems(userUID)
    }
}