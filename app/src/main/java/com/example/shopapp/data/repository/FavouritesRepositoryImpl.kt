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
                val documentId = favouritesRef.document().id
                favouritesRef.document(documentId).set(
                    mapOf(
                        "favouriteId" to documentId,
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

    override suspend fun getUserFavourite(
        userUID: String,
        productId: Int
    ): Flow<Resource<List<Favourite>>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val favourite = favouritesRef
                    .whereEqualTo("userUID", userUID)
                    .whereEqualTo("productId", productId)
                    .get()
                    .await()
                    .documents
                    .mapNotNull { snapshot ->
                        snapshot.toObject(Favourite::class.java)
                    }
                if(favourite.isEmpty()) {
                    emit(Resource.Success(emptyList()))
                }
                else {
                    emit(Resource.Success(favourite))
                }
            }
            catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage as String))
            }

            emit(Resource.Loading(false))
        }
    }
}