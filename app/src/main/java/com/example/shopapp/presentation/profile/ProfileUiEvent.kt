package com.example.shopapp.presentation.profile

sealed class ProfileUiEvent {
    data class ShowErrorMessage(val message: String): ProfileUiEvent()
    object NavigateBack: ProfileUiEvent()
}