package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.UserStorageRepository
import com.example.shopapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetUserPointsUseCase(
    private val userStorageRepository: UserStorageRepository
) {
    suspend operator fun invoke(userUID: String): Flow<Resource<Int>> {
        return userStorageRepository.getUserPoints(userUID)
    }
}