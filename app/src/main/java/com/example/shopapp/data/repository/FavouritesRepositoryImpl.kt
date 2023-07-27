package com.example.shopapp.data.repository

import com.example.shopapp.domain.model.Favourite
import com.example.shopapp.domain.repository.FavouritesRepository
import com.example.shopapp.util.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavouritesRepositoryImpl @Inject constructor(
    private val favouritesRef: CollectionReference
): FavouritesRepository {
    override suspend fun addProductToFavourites(
        productId: Int,
        userUID: String
    ): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                favouritesRef.document().set(
                    mapOf(
                        "userUID" to userUID,
                        "productId" to productId
                    )
                ).await()
                emit(Resource.Success(true))
            }
            catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage as String))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getUserFavourites(userUID: String) = callbackFlow {
        val snapshotListener = favouritesRef
            .whereEqualTo("userUID", userUID)
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val userFavourites = snapshot.toObjects(Favourite::class.java)
                    Resource.Success(userFavourites)
                }
                else {
                    Resource.Error(e!!.localizedMessage as String)
                }
                trySend(response)
            }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun getFavouriteId(productId: Int, userUID: String) = callbackFlow {
        val snapshotListener = favouritesRef
            .whereEqualTo("productId",productId)
            .whereEqualTo("userUID", userUID)
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val userFavouriteId = snapshot.documents[0].id
                    Resource.Success(userFavouriteId)
                }
                else {
                    Resource.Error(e!!.localizedMessage as String)
                }
                trySend(response)
            }

        awaitClose {
            snapshotListener.remove()
        }
    }

    override suspend fun deleteProductFromFavourites(
        favouriteId: String
    ): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                favouritesRef.document(favouriteId)
                    .delete()
                    .await()
                emit(Resource.Success(true))
            }
            catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage as String))
            }

            emit(Resource.Loading(false))
        }
    }
}