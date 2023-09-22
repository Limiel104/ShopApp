package com.example.shopapp.presentation.orders

sealed class OrdersUiEvent {
    data class ShowErrorMessage(val message: String): OrdersUiEvent()
}