package com.example.shopapp.domain.use_case

import com.example.shopapp.domain.repository.AuthRepository

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke() {
        authRepository.logout()
    }
}