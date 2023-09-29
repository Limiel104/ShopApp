package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.util.OrderOrder

class SortOrdersUseCase {
    operator fun invoke(orderOrder: OrderOrder, orders: List<Order>): List<Order> {
        return when(orderOrder) {
            is OrderOrder.DateAscending -> orders.sortedBy { it.date }
            is OrderOrder.DateDescending -> orders.sortedByDescending { it.date }
        }
    }
}