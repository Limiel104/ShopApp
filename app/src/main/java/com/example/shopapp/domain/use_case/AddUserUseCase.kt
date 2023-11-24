package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.model.User
import com.example.shopapp.domain.repository.UserStorageRepository
import com.example.shopapp.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class AddUserUseCase(
    private val userStorageRepository: UserStorageRepository
) {
    suspend operator fun invoke(user: User): Flow<Resource<Boolean>> {
        return userStorageRepository.addUser(user)
    }
}