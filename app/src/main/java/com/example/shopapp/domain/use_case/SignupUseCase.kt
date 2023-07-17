package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.AuthRepository
import com.example.shopapp.util.Resource
import com.google.firebase.auth.FirebaseUser

class SignupUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<FirebaseUser> {
        return authRepository.signup(email, password)
    }
}