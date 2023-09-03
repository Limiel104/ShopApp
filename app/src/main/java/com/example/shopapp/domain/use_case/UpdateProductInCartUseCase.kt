package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.repository.CartRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateProductInCartUseCase(
    private val cartRepository: CartRepository
) {
    suspend operator fun invoke(cartItem: CartItem): Flow<Resource<Boolean>> {
        return cartRepository.updateProductInCart(cartItem)
    }
}