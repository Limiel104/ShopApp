package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    suspend fun addProductToFavourites(productId: Int, userUID: String): Flow<Resource<Boolean>>

    suspend fun getUserFavourites(userUID: String): Flow<Resource<List<Favourite>>>

    suspend fun deleteProductFromFavourites(favouriteId: String): Flow<Resource<Boolean>>

    suspend fun getUserFavourite(userUID: String, productId: Int): Flow<Resource<List<Favourite>>>
}