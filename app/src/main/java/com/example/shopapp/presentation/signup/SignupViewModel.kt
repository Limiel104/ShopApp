package com.example.shopapp.presentation.signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.SIGNUP_VM
import com.example.shopapp.util.Constants.TAG
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
                Log.i(TAG,"login " + _signupState.value.email)
            }
            is SignupEvent.EnteredPassword -> {
                _signupState.value = signupState.value.copy(
                    password = event.value
                )
                Log.i(TAG,"password " + _signupState.value.password)
            }
            is SignupEvent.EnteredConfirmPassword -> {
                _signupState.value = signupState.value.copy(
                    confirmPassword = event.value
                )
                Log.i(TAG,"password " + _signupState.value.confirmPassword)
            }
            is SignupEvent.Signup -> {
                val email = _signupState.value.email
                val password = _signupState.value.password
                val confirmPassword = _signupState.value.confirmPassword
                signup(email,password,confirmPassword)
            }
        }
    }

    private fun signup(email: String, password: String, confirmPassword: String) {
        viewModelScope.launch {
            if(isValidationSuccessful(email,password,confirmPassword)){
                _eventFlow.emit(SignupUiEvent.Signup)
            }
            else {
                Log.i("TAG", "Invalid signup credentials")
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