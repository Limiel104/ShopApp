package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.FirebaseOrder
import com.example.shopapp.domain.model.Order
import com.example.shopapp.domain.model.Product

class SetOrdersUseCase {
    operator fun invoke(firebaseOrders: List<FirebaseOrder>, products: List<Product>): List<Order> {

        val orders: MutableList<Order> = mutableListOf()

        for(firebaseOrder in firebaseOrders) {
            val orderProducts: MutableList<CartProduct> = mutableListOf()

            for(firebaseOrderProduct in firebaseOrder.products) {
                val orderProduct = products.first {
                    it.id == firebaseOrderProduct.key.toInt()
                }

                val cartProduct = CartProduct(
                    id = orderProduct.id,
                    title = orderProduct.title,
                    price = orderProduct.price,
                    imageUrl = orderProduct.imageUrl,
                    amount = firebaseOrderProduct.value
                )
                orderProducts.add(cartProduct)
            }

            val order = Order(
                orderId = firebaseOrder.orderId,
                date = firebaseOrder.date,
                totalAmount = firebaseOrder.totalAmount,
                products = orderProducts
            )

            orders.add(order)
        }

        return orders
    }
}