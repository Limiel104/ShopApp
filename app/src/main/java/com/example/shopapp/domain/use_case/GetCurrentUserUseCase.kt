package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser

class GetCurrentUserUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): FirebaseUser? {
        return authRepository.currentUser
    }
}