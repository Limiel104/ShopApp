package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface OrdersRepository {

    suspend fun addOrder(firebaseOrder: FirebaseOrder): Flow<Resource<Boolean>>

    suspend fun getUserOrders(userUID: String): Flow<Resource<List<FirebaseOrder>>>
}