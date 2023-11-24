package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.repository.OrdersRepository
import com.example.shopapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class AddOrderUseCase(
    private val ordersRepository: OrdersRepository
) {
    suspend operator fun invoke(firebaseOrder: FirebaseOrder): Flow<Resource<Boolean>> {
        return ordersRepository.addOrder(firebaseOrder)
    }
}