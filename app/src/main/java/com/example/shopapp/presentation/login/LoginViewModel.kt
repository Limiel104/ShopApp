package com.example.shopapp.presentation.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.presentation.common.Constants.LOGIN_VM
import com.example.shopapp.presentation.common.Constants.TAG
import com.example.shopapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _loginState = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    private val _loginEventChannel = Channel<LoginUiEvent>()
    val loginEventChannelFlow = _loginEventChannel.receiveAsFlow()

    init {
        Log.i(TAG,LOGIN_VM)
    }

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EnteredEmail -> {
                _loginState.value = loginState.value.copy(
                    email = event.value
                )
            }
            is LoginEvent.EnteredPassword -> {
                _loginState.value = loginState.value.copy(
                    password = event.value
                )
            }
            is LoginEvent.OnSignupButtonSelected -> {
                viewModelScope.launch {
                    _loginEventChannel.send(LoginUiEvent.NavigateToSignup)
                }
            }
            is LoginEvent.Login -> {
                val email = _loginState.value.email
                val password = _loginState.value.password

                if(isValidationSuccessful(email,password)) {
                    login(email,password)
                }
                else {
                    Log.i(TAG, "Form validation error")
                }
            }
            is LoginEvent.OnGoBack -> {
                viewModelScope.launch {
                    _loginEventChannel.send(LoginUiEvent.NavigateBack)
                }
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            shopUseCases.loginUseCase(email,password).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading = ${response.isLoading}")
                        _loginState.value = loginState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                    Log.i(TAG,"Login was successful")
                    _loginEventChannel.send(LoginUiEvent.Login)
                    }
                    is Resource.Error -> {
                        Log.i("TAG", "Login Error")
                        _loginState.value = loginState.value.copy(
                            emailError = null,
                            passwordError = null
                        )

                        val errorMessage = response.message
                        _loginEventChannel.send(LoginUiEvent.ShowErrorMessage(errorMessage!!))
                    }
                }
            }
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