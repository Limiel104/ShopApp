package com.example.shopapp.data.repository

import com.example.shopapp.domain.repository.AuthRepository
import com.example.shopapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email,password).await()
            Resource.Success(result.user!!)
        }
        catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage!!)
        }
    }

    override suspend fun signup(email: String, password: String): Resource<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().build())
            Resource.Success(result.user!!)
        }
        catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage!!)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}