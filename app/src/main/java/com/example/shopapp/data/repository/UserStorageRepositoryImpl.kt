package com.example.shopapp.data.repository

import com.example.shopapp.domain.model.User
import com.example.shopapp.domain.repository.UserStorageRepository
import com.example.shopapp.util.Resource
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class UserStorageRepositoryImpl @Inject constructor(
    private val usersRef: CollectionReference
): UserStorageRepository {
    override suspend fun addUser(userUID: String): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                usersRef.document(userUID).set(
                    mapOf(
                        "userUID" to userUID,
                        "firstName" to "",
                        "lastName" to "",
                        "points" to 0
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

    override suspend fun getUser(userUID: String) = callbackFlow {
        val snapshotListener = usersRef
            .whereEqualTo("userUID", userUID)
            .addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val user = snapshot.toObjects(User::class.java)
                    Resource.Success(user)
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

    override suspend fun updateUser(user: User): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                usersRef.document(user.userUID).update(
                    mapOf(
                        "userUID" to user.userUID,
                        "firstName" to "",
                        "lastName" to "",
                        "points" to 0
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
}