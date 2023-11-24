package com.example.shopapp.presentation.signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.model.Address
import com.example.shopapp.domain.model.User
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.presentation.common.Constants.SIGNUP_VM
import com.example.shopapp.presentation.common.Constants.TAG
import com.example.shopapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _signupState = mutableStateOf(SignupState())
    val signupState: State<SignupState> = _signupState

    private val _signupEventChannel = Channel<SignupUiEvent>()
    val signupEventChannelFlow = _signupEventChannel.receiveAsFlow()

    init {
        Log.i(TAG,SIGNUP_VM)
    }

    fun onEvent(event: SignupEvent) {
        when(event) {
            is SignupEvent.EnteredEmail -> {
                _signupState.value = signupState.value.copy(
                    email = event.value
                )
            }
            is SignupEvent.EnteredPassword -> {
                _signupState.value = signupState.value.copy(
                    password = event.value
                )
            }
            is SignupEvent.EnteredConfirmPassword -> {
                _signupState.value = signupState.value.copy(
                    confirmPassword = event.value
                )
            }
            is SignupEvent.EnteredFirstName -> {
                _signupState.value = signupState.value.copy(
                    firstName = event.value
                )
            }
            is SignupEvent.EnteredLastName -> {
                _signupState.value = signupState.value.copy(
                    lastName = event.value
                )
            }
            is SignupEvent.EnteredStreet -> {
                _signupState.value = signupState.value.copy(
                    street = event.value
                )
            }
            is SignupEvent.EnteredCity -> {
                _signupState.value = signupState.value.copy(
                    city = event.value
                )
            }
            is SignupEvent.EnteredZipCode -> {
                _signupState.value = signupState.value.copy(
                    zipCode = event.value
                )
            }
            is SignupEvent.Signup -> {
                val email = _signupState.value.email
                val password = _signupState.value.password
                val confirmPassword = _signupState.value.confirmPassword
                val firstName = _signupState.value.firstName
                val lastName = _signupState.value.lastName
                val street = _signupState.value.street
                val city = _signupState.value.city
                val zipCode = _signupState.value.zipCode

                if(isValidationSuccessful(email,password,confirmPassword,firstName,lastName,street,city,zipCode)){
                    signup(email,password)
                }
                else {
                    Log.i(TAG, "Form validation error")
                }
            }
            is SignupEvent.OnGoBack -> {
                viewModelScope.launch {
                    _signupEventChannel.send(SignupUiEvent.NavigateBack)
                }
            }
        }
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            shopUseCases.signupUseCase(email,password).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading = ${response.isLoading}")
                       _signupState.value = signupState.value.copy(
                           isLoading = response.isLoading
                       )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Signup was successful")
                        addUser()
                    }
                    is Resource.Error -> {
                        Log.i(TAG, "Signup Error")
                        _signupState.value = signupState.value.copy(
                            emailError =  null,
                            passwordError = null,
                            confirmPasswordError = null,
                            firstNameError = null,
                            lastNameError = null,
                            streetError = null,
                            cityError = null,
                            zipCodeError = null
                        )

                        val errorMessage = response.message
                        _signupEventChannel.send(SignupUiEvent.ShowErrorMessage(errorMessage!!))
                    }
                }
            }
        }
    }

    fun addUser() {
        viewModelScope.launch {
            val userUID = shopUseCases.getCurrentUserUseCase()!!.uid
            val user = User(
                userUID = userUID,
                firstName = _signupState.value.firstName,
                lastName = _signupState.value.lastName,
                address = Address(
                    street = _signupState.value.street,
                    city = _signupState.value.city,
                    zipCode = _signupState.value.zipCode
                )
            )

            shopUseCases.addUserUseCase(user).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading = ${response.isLoading}")
                        _signupState.value = signupState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        Log.i(TAG,"Added user successfully")
                        _signupEventChannel.send(SignupUiEvent.Signup)
                    }
                    is Resource.Error -> {
                        val errorMessage = response.message
                        _signupEventChannel.send(SignupUiEvent.ShowErrorMessage(errorMessage!!))
                    }
                }
            }
        }
    }

    fun isValidationSuccessful(
        email: String,
        password: String,
        confirmPassword: String,
        firstName: String,
        lastName: String,
        street: String,
        city: String,
        zipCode: String
    ): Boolean {
        val emailValidationResult = shopUseCases.validateEmailUseCase(email)
        val passwordValidationResult = shopUseCases.validateSignupPasswordUseCase(password)
        val confirmPasswordValidationResult = shopUseCases.validateConfirmPasswordUseCase(password, confirmPassword)
        val firstNameValidationResult = shopUseCases.validateNameUseCase(firstName)
        val lastNameValidationResult = shopUseCases.validateNameUseCase(lastName)
        val streetValidationResult = shopUseCases.validateStreetUseCase(street)
        val cityValidationResult = shopUseCases.validateCityUseCase(city)
        val zipCodeValidationResult = shopUseCases.validateZipCodeUseCase(zipCode)

        val hasError = listOf(
            emailValidationResult,
            passwordValidationResult,
            confirmPasswordValidationResult,
            firstNameValidationResult,
            lastNameValidationResult,
            streetValidationResult,
            cityValidationResult,
            zipCodeValidationResult
        ).any { !it.isSuccessful }

        if(hasError) {
            _signupState.value = signupState.value.copy(
                emailError =  emailValidationResult.errorMessage,
                passwordError = passwordValidationResult.errorMessage,
                confirmPasswordError = confirmPasswordValidationResult.errorMessage,
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
}