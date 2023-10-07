package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.User
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserStorageRepository {

    suspend fun addUser(user: User): Flow<Resource<Boolean>>

    suspend fun getUser(userUID: String): Flow<Resource<List<User>>>

    suspend fun getUserPoints(userUID: String): Flow<Resource<Int>>

    suspend fun updateUser(user: User): Flow<Resource<Boolean>>

    suspend fun updateUserPints(userUID: String, points: Int): Flow<Resource<Boolean>>
}