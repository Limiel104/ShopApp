package com.example.shopapp.presentation.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.Address
import com.example.shopapp.domain.model.User
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.presentation.common.Constants.PROFILE_VM
import com.example.shopapp.presentation.common.Constants.TAG
import com.example.shopapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _profileState = mutableStateOf(ProfileState())
    val profileState: State<ProfileState> = _profileState

    private val _profileEventChannel = Channel<ProfileUiEvent>()
    val profileEventChannelFlow = _profileEventChannel.receiveAsFlow()

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
                val user = User(
                    userUID = _profileState.value.userUID,
                    firstName = _profileState.value.firstName,
                    lastName = _profileState.value.lastName,
                    address = Address(
                        street = _profileState.value.street,
                        city = _profileState.value.city,
                        zipCode = _profileState.value.zipCode
                    ),
                    points = _profileState.value.points
                )

                if(isValidationSuccessful(
                        user.firstName,
                        user.lastName,
                        user.address.street,
                        user.address.city,
                        user.address.zipCode
                    )
                ) {
                    saveChanges(user)
                }
                else {
                    Log.i(TAG, "Form validation error")
                }
            }
            is ProfileEvent.OnGoBack -> {
                viewModelScope.launch {
                    _profileEventChannel.send(ProfileUiEvent.NavigateBack)
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
                            if(user.isNotEmpty()) {
                                _profileState.value = profileState.value.copy(
                                    firstName = user[0].firstName,
                                    lastName = user[0].lastName,
                                    street = user[0].address.street,
                                    city = user[0].address.city,
                                    zipCode = user[0].address.zipCode,
                                    points = user[0].points
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _profileEventChannel.send(ProfileUiEvent.ShowErrorMessage(response.message.toString()))
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

    fun saveChanges(user: User) {
        viewModelScope.launch {
            shopUseCases.updateUserUseCase(user).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading: ${response.isLoading}")
                        _profileState.value = profileState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Updated user successfully")
                        _profileEventChannel.send(ProfileUiEvent.NavigateBack)
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _profileEventChannel.send(ProfileUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }
}