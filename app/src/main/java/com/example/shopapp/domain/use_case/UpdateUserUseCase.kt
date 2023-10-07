package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.User
import com.example.shopapp.domain.repository.UserStorageRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateUserUseCase(
    private val userStorageRepository: UserStorageRepository
) {
    suspend operator fun invoke(user: User): Flow<Resource<Boolean>> {
        return userStorageRepository.updateUser(user)
    }
}