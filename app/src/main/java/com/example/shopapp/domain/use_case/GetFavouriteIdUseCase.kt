package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Favourite

class GetFavouriteIdUseCase {
    operator fun invoke(favourites: List<Favourite>, productId: Int): String {
        val favourite = favourites.find { favourite ->
            favourite.productId == productId
        }
        return favourite!!.favouriteId
    }
}