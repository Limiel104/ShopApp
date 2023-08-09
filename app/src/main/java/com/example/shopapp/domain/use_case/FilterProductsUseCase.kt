package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Product
import com.example.shopapp.util.Category

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
            val productPrice = product.price.replace(",",".").replace("[^0-9.]".toRegex(),"").toFloat()
            if(productPrice in minPrice..maxPrice) {
                filteredProductsByPrice.add(product)
            }
        }

        return filteredProductsByPrice
    }
}

private fun returnCategory(categoryId: String): String {
    return when(categoryId) {
        Category.Men.id -> Category.Men.title
        Category.Women.id -> Category.Women.title
        Category.Jewelery.id -> Category.Jewelery.title
        Category.Electronics.id -> Category.Electronics.title
        else -> ""
    }
}