package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.Order
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {

    suspend fun addOrder(order: Order): Flow<Resource<Boolean>>

    suspend fun getUserOrders(userUID: String): Flow<Resource<List<Order>>>
}