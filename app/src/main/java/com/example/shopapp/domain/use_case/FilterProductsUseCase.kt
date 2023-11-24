package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product

class FilterProductsUseCase() {
    operator fun invoke(
        products: List<Product>,
        minPrice: Float,
        maxPrice: Float,
        categoryFilterMap: Map<String,Boolean>
    ): List<Product> {
        val filteredProductsByCategory = products.filter { product ->
            val category = returnCategory(product.category)
            categoryFilterMap[category] == true
        }

        val filteredProductsByPrice: MutableList<Product> = mutableListOf()

        filteredProductsByCategory.forEach { product ->
            if(product.price.toFloat() in minPrice..maxPrice) {
                filteredProductsByPrice.add(product)
            }
        }

        return filteredProductsByPrice
    }
}

private fun returnCategory(categoryId: String): String {
    return when(categoryId) {
        "men's clothing" -> "Men's clothing"
        "women's clothing" -> "Women's clothing"
        "jewelery" -> "Jewelery"
        "electronics" -> "Electronics"
        else -> ""
    }
}