package com.example.shopapp.domain.repository

import com.example.shopapp.domain.model.User
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface UserStorageRepository {

    suspend fun addUser(userUID: String): Resource<Boolean>

    suspend fun getUser(userUID: String): Flow<Resource<List<User>>>

    suspend fun updateUser(user: User): Resource<Boolean>
}