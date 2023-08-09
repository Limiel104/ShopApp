package com.example.shopapp.domain.util

sealed class ProductOrder() {
    class NameAscending(): ProductOrder()
    class NameDescending(): ProductOrder()
    class PriceAscending(): ProductOrder()
    class PriceDescending(): ProductOrder()
}