package com.example.shopapp.presentation.orders

import com.example.shopapp.domain.util.OrderOrder

sealed class OrdersEvent {
    data class OnOrderSelected(val value: String): OrdersEvent()
    data class OnOrderChange(val value: OrderOrder): OrdersEvent()
    object ToggleSortSection: OrdersEvent()
}