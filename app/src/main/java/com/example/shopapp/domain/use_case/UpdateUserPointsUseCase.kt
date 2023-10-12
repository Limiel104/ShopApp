package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.UserStorageRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class UpdateUserPointsUseCase(
    private val userStorageRepository: UserStorageRepository
) {
    suspend operator fun invoke(user: String, points: Int): Flow<Resource<Boolean>> {
        return userStorageRepository.updateUserPints(user,points)
    }
}