package com.example.shopapp.presentation.orders

import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.util.OrderOrder

data class OrdersState (
    val firebaseOrders: List<FirebaseOrder> = emptyList(),
    val orders: List<Order> = emptyList(),
    val userUID: String = "",
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val orderOrder: OrderOrder = OrderOrder.DateDescending(),
    val isSortSectionVisible: Boolean = false
)