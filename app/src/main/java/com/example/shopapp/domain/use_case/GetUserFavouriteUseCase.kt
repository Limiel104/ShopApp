package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.repository.FavouritesRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetUserFavouriteUseCase(
    private val favouritesRepository: FavouritesRepository
) {
    suspend operator fun invoke(userUID: String, productId: Int): Flow<Resource<List<Favourite>>> {
        return favouritesRepository.getUserFavourite(userUID, productId)
    }
}