package com.example.shopapp.domain.util

sealed class OrderOrder() {
    class DateAscending(): OrderOrder()
    class DateDescending(): OrderOrder()
}