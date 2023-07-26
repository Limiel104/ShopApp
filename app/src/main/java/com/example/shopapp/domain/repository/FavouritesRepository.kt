package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    suspend fun addFavouriteProduct(productId: Int, userUID: String): Flow<Resource<Boolean>>

    suspend fun getUserFavourites(userUID: String): Flow<Resource<List<Favourite>>>

    suspend fun getUserFavouriteId(productId: Int, userUID: String): Flow<Resource<String>>

    suspend fun deleteFavouriteProduct(userFavouriteId: String): Flow<Resource<Boolean>>
}