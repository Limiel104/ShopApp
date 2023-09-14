package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.repository.OrdersRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetUserOrdersUseCase(
    private val ordersRepository: OrdersRepository
) {
    suspend operator fun invoke(userUID: String): Flow<Resource<List<Order>>> {
        return ordersRepository.getUserOrders(userUID)
    }
}