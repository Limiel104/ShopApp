package com.example.shopapp.presentation.signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.SIGNUP_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _signupState = mutableStateOf(SignupState())
    val signupState: State<SignupState> = _signupState

    private val _eventFlow = MutableSharedFlow<SignupUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
            is SignupEvent.Signup -> {
                val email = _signupState.value.email
                val password = _signupState.value.password
                val confirmPassword = _signupState.value.confirmPassword

                if(isValidationSuccessful(email,password,confirmPassword)){
                    signup(email,password)
                }
                else {
                    Log.i(TAG, "Form validation error")
                }
            }
        }
    }

    private fun signup(email: String, password: String) {
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
                        _eventFlow.emit(SignupUiEvent.Signup)
                    }
                    is Resource.Error -> {
                        Log.i(TAG, "Signup Error")
                        _signupState.value = signupState.value.copy(
                            emailError =  null,
                            passwordError = null,
                            confirmPasswordError = null
                        )

                        val errorMessage = response.message
                        _eventFlow.emit(SignupUiEvent.ShowErrorMessage(errorMessage!!))
                    }
                }
            }
        }
    }

    fun isValidationSuccessful(
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        val emailValidationResult = shopUseCases.validateEmailUseCase(email)
        val passwordValidationResult = shopUseCases.validateSignupPasswordUseCase(password)
        val confirmPasswordValidationResult = shopUseCases.validateConfirmPasswordUseCase(password, confirmPassword)

        val hasError = listOf(
            emailValidationResult,
            passwordValidationResult,
            confirmPasswordValidationResult
        ).any { !it.isSuccessful }

        if(hasError) {
            _signupState.value = signupState.value.copy(
                emailError =  emailValidationResult.errorMessage,
                passwordError = passwordValidationResult.errorMessage,
                confirmPasswordError = confirmPasswordValidationResult.errorMessage
            )
            return false
        }
        return true
    }
}