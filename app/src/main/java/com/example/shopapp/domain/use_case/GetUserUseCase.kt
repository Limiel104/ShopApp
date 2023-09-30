package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.User
import com.example.shopapp.domain.repository.UserStorageRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetUserUseCase(
    private val userStorageRepository: UserStorageRepository
) {
    suspend operator fun invoke(userUID: String): Flow<Resource<List<User>>> {
        return userStorageRepository.getUser(userUID)
    }
}