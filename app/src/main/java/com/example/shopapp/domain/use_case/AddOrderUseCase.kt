package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.repository.OrdersRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class AddOrderUseCase(
    private val ordersRepository: OrdersRepository
) {
    suspend operator fun invoke(order: Order): Flow<Resource<Boolean>> {
        return ordersRepository.addOrder(order)
    }
}