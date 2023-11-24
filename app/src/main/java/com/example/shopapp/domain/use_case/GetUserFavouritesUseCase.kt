package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.repository.FavouritesRepository
import com.example.shopapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetUserFavouritesUseCase(
    private val favouritesRepository: FavouritesRepository
) {
    suspend operator fun invoke(userUID: String): Flow<Resource<List<Favourite>>> {
        return favouritesRepository.getUserFavourites(userUID)
    }
}