package com.example.shopapp.presentation.orders

sealed class OrdersEvent {
    data class OnOrderSelected(val value: String): OrdersEvent()
}