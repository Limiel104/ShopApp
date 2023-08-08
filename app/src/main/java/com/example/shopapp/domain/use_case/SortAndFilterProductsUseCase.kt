package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product

class SortAndFilterProductsUseCase() {
    operator fun invoke(
        products: List<Product>,
        minPrice: Float? = null,
        maxPrice: Float? = null
    ): List<Product> {
        val resultProducts: MutableList<Product> = mutableListOf()

        if(minPrice != null && maxPrice != null) {
            products.forEach { product ->
                val productPrice = product.price.replace(",",".").replace("[^0-9.]".toRegex(),"").toFloat()
                if(productPrice in minPrice..maxPrice) {
                    resultProducts.add(product)
                }
            }
        }
        return resultProducts
    }
}