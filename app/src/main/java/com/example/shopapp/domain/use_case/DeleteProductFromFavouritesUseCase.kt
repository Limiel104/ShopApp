package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.FavouritesRepository
import com.example.shopapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class DeleteProductFromFavouritesUseCase(
    private val favouritesRepository: FavouritesRepository
) {
    suspend operator fun invoke(favouriteId: String): Flow<Resource<Boolean>> {
        return favouritesRepository.deleteProductFromFavourites(favouriteId)
    }
}