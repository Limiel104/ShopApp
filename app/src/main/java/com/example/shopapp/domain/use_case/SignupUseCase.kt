package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.AuthRepository
import com.example.shopapp.domain.util.Resource
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class SignupUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<FirebaseUser>> {
        return authRepository.signup(email, password)
    }
}