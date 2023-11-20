package com.example.shopapp.data.repository

import com.example.shopapp.domain.model.User
import com.example.shopapp.domain.repository.UserStorageRepository
import com.example.shopapp.domain.util.Resource
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
    override suspend fun addUser(user: User): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                usersRef.document(user.userUID).set(
                    mapOf(
                        "userUID" to user.userUID,
                        "firstName" to user.firstName,
                        "lastName" to user.lastName,
                        "address" to user.address,
                        "points" to user.points
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

    override suspend fun getUserPoints(userUID: String): Flow<Resource<Int>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val userPoints = usersRef
                    .whereEqualTo("userUID", userUID)
                    .get()
                    .await()
                    .documents
                    .mapNotNull { snapshot ->
                        snapshot.toObject(User::class.java)
                    }
                if(userPoints.isEmpty()) {
                    emit(Resource.Success(-1))
                }
                else {
                    emit(Resource.Success(userPoints[0].points))
                }
            }
            catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage as String))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateUser(user: User): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                usersRef.document(user.userUID).update(
                    mapOf(
                        "userUID" to user.userUID,
                        "firstName" to user.firstName,
                        "lastName" to user.lastName,
                        "address" to user.address
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

    override suspend fun updateUserPints(userUID: String, points: Int): Flow<Resource<Boolean>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                usersRef.document(userUID).update(
                    mapOf(
                        "points" to points
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