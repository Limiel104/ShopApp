package com.example.shopapp.presentation.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.LOGIN_VM
import com.example.shopapp.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG,LOGIN_VM)
    }

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EnteredEmail -> {
                _loginState.value = loginState.value.copy(
                    email = event.value
                )
                Log.i(TAG,"login " + _loginState.value.email)
            }
            is LoginEvent.EnteredPassword -> {
                _loginState.value = loginState.value.copy(
                    password = event.value
                )
                Log.i(TAG,"password " + _loginState.value.password)
            }
            is LoginEvent.OnSignupButtonSelected -> {
                viewModelScope.launch {
                    _eventFlow.emit(LoginUiEvent.NavigateToSignup)
                }
            }
            is LoginEvent.Login -> {
                val email = _loginState.value.email
                val password = _loginState.value.password
                login(email,password)
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            if(isValidationSuccessful(email,password))
            _eventFlow.emit(LoginUiEvent.Login)
        }
    }

    fun isValidationSuccessful(email: String, password: String): Boolean {
        val emailValidationResult = shopUseCases.validateEmailUseCase(email)
        val passwordValidationResult = shopUseCases.validateLoginPasswordUseCase(password)

        val hasError = listOf(
            emailValidationResult,
            passwordValidationResult
        ).any { !it.isSuccessful }

        if (hasError) {
            _loginState.value = loginState.value.copy(
                emailError = emailValidationResult.errorMessage,
                passwordError = passwordValidationResult.errorMessage
            )
            return false
        }
        return true
    }
}