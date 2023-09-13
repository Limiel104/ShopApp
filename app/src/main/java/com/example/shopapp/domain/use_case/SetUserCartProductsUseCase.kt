package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.CartItem
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.domain.model.Product

class SetUserCartProductsUseCase {
    operator fun invoke(cartItems: List<CartItem>, products: List<Product>): List<CartProduct> {

        val cartProducts: MutableList<CartProduct> = mutableListOf()
        val indexes: MutableList<Int> = mutableListOf()

        for(cartItem in cartItems) {
            indexes.add(cartItem.productId)
        }

        for(product in products) {
            if(indexes.contains(product.id)) {
                val amount = cartItems.first { cartItem ->
                    cartItem.productId == product.id
                }.amount

                cartProducts.add(
                    CartProduct(
                        id = product.id,
                        title = product.title,
                        price = product.price,
                        imageUrl = product.imageUrl,
                        amount = amount
                    )
                )
            }
        }

        return cartProducts
    }
}