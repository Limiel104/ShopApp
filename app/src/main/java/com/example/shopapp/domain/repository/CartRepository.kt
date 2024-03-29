package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {

    suspend fun addProductToCart(userUID: String, productId: Int, amount: Int): Flow<Resource<Boolean>>

    suspend fun getUserCartItems(userUID: String): Flow<Resource<List<CartItem>>>

    suspend fun deleteProductFromCart(cartItemId: String): Flow<Resource<Boolean>>

    suspend fun updateProductInCart(cartItem: CartItem): Flow<Resource<Boolean>>

    suspend fun getUserCartItem(userUID: String, productId: Int): Flow<Resource<List<CartItem>>>
}