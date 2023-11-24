package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.repository.OrdersRepository
import com.example.shopapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetUserOrdersUseCase(
    private val ordersRepository: OrdersRepository
) {
    suspend operator fun invoke(userUID: String): Flow<Resource<List<FirebaseOrder>>> {
        return ordersRepository.getUserOrders(userUID)
    }
}