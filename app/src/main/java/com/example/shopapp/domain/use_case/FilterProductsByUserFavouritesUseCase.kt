package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.model.Product

class FilterProductsByUserFavouritesUseCase {
    operator fun invoke(products: List<Product>, favourites: List<Favourite>): List<Product> {
        val favouriteProducts: MutableList<Product> = mutableListOf()
        val indexes: MutableList<Int> = mutableListOf()

        for(favourite in favourites) {
            indexes.add(favourite.productId)
        }

        for(product in products) {
            if(indexes.contains(product.id)) {
                favouriteProducts.add(
                    Product(
                        id = product.id,
                        title = product.title,
                        price = product.price,
                        description = product.description,
                        category = product.category,
                        imageUrl = product.imageUrl,
                        isInFavourites = true
                    )
                )
            }
        }

        return favouriteProducts
    }
}