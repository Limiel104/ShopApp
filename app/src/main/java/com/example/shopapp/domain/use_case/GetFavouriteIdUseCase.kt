package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.FavouritesRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetFavouriteIdUseCase(
    private val favouritesRepository: FavouritesRepository
) {
    suspend operator fun invoke(productId: Int, userUID: String): Flow<Resource<String>> {
        return favouritesRepository.getFavouriteId(productId,userUID)
    }
}