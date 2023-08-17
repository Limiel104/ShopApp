package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.util.ProductOrder

class SortProductsUseCase {
    operator fun invoke(productOrder: ProductOrder, products: List<Product>): List<Product> {
        return when(productOrder) {
            is ProductOrder.NameAscending -> products.sortedBy { it.title.lowercase() }
            is ProductOrder.NameDescending -> products.sortedByDescending { it.title.lowercase() }
            is ProductOrder.PriceAscending -> products.sortedBy { it.price.replace(",",".").replace("[^0-9.]".toRegex(),"").toFloat() }
            is ProductOrder.PriceDescending -> products.sortedByDescending { it.price.replace(",",".").replace("[^0-9.]".toRegex(),"").toFloat() }
        }
    }
}