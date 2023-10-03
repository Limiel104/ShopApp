package com.example.shopapp.presentation.profil

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.PROFILE_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _eventFlow = MutableSharedFlow<ProfileUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG,PROFILE_VM)

        savedStateHandle.get<String>("userUID")?.let { userUID ->
            _profileState.value = profileState.value.copy(
                userUID = userUID
            )
        }

        getUser(_profileState.value.userUID)
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
            is ProfileEvent.Save -> {
                val firstName = _profileState.value.firstName
                val lastName = _profileState.value.lastName
                val street = _profileState.value.street
                val city = _profileState.value.city
                val zipCode = _profileState.value.zipCode

                if(isValidationSuccessful(firstName,lastName,street,city,zipCode)){
                    saveChanges()
                }
                else {
                    Log.i(TAG, "Form validation error")
                }
            }
        }
    }

    fun getUser(userUID: String) {
        viewModelScope.launch {
            shopUseCases.getUserUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading user: ${response.isLoading}")
                        _profileState.value = profileState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { user ->
                            Log.i(TAG, "User: $user")
                            _profileState.value = profileState.value.copy(
                                firstName = user[0].firstName,
                                lastName = user[0].lastName,
                                street = user[0].address.street,
                                city = user[0].address.city,
                                zipCode = user[0].address.zipCode
                            )
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(ProfileUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun isValidationSuccessful(
        firstName: String,
        lastName: String,
        street: String,
        city: String,
        zipCode: String
    ): Boolean {
        val firstNameValidationResult = shopUseCases.validateNameUseCase(firstName)
        val lastNameValidationResult = shopUseCases.validateNameUseCase(lastName)
        val streetValidationResult = shopUseCases.validateStreetUseCase(street)
        val cityValidationResult = shopUseCases.validateCityUseCase(city)
        val zipCodeValidationResult = shopUseCases.validateZipCodeUseCase(zipCode)

        val hasError = listOf(
            firstNameValidationResult,
            lastNameValidationResult,
            streetValidationResult,
            cityValidationResult,
            zipCodeValidationResult
        ).any { !it.isSuccessful }

        if(hasError) {
            _profileState.value = profileState.value.copy(
                firstNameError = firstNameValidationResult.errorMessage,
                lastNameError = lastNameValidationResult.errorMessage,
                streetError = streetValidationResult.errorMessage,
                cityError = cityValidationResult.errorMessage,
                zipCodeError = zipCodeValidationResult.errorMessage
            )
            return false
        }
        return true
    }

    fun saveChanges() {

    }
}