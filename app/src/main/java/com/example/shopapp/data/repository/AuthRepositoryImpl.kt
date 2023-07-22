package com.example.shopapp.data.repository

import com.example.shopapp.domain.repository.AuthRepository
import com.example.shopapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
                emit(Resource.Success(result.user!!))
            }
            catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage!!))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun signup(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
                emit(Resource.Success(result.user!!))
            }
            catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage!!))
            }

            emit(Resource.Loading(false))
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}