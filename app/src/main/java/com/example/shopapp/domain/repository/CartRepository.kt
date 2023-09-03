package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.CartElement
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun addProductToCart(userUID: String, productId: Int, amount: Int): Flow<Resource<Boolean>>

    suspend fun getCartItems(userUID: String): Flow<Resource<List<CartElement>>>

    suspend fun deleteProductFromCart(cartItemId: String): Flow<Resource<Boolean>>

    suspend fun updateProductInCart(cartElement: CartElement): Flow<Resource<Boolean>>
}