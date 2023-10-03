package com.example.shopapp.presentation.profil

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.PROFILE_VM
import com.example.shopapp.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _eventFlow = MutableSharedFlow<ProfileUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG,PROFILE_VM)
    }

    fun onEvent(event: ProfileEvent) {
        when(event) {
            is ProfileEvent.EnteredFirstName -> {
                _profileState.value = profileState.value.copy(
                    firstName = event.value
                )
            }
            is ProfileEvent.EnteredLastName -> {
                _profileState.value = profileState.value.copy(
                    lastName = event.value
                )
            }
            is ProfileEvent.EnteredStreet -> {
                _profileState.value = profileState.value.copy(
                    street = event.value
                )
            }
            is ProfileEvent.EnteredCity -> {
                _profileState.value = profileState.value.copy(
                    city = event.value
                )
            }
            is ProfileEvent.EnteredZipCode -> {
                _profileState.value = profileState.value.copy(
                    zipCode = event.value
                )
            }
            is ProfileEvent.Save -> TODO()
        }
    }
}