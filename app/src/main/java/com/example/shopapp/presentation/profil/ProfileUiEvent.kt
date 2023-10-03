package com.example.shopapp.presentation.profil

sealed class ProfileUiEvent {
    data class ShowErrorMessage(val message: String): ProfileUiEvent()
    object Save: ProfileUiEvent()
}