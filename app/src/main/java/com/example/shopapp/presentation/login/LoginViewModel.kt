package com.example.shopapp.presentation.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.util.Constants.LOGIN_VM
import com.example.shopapp.util.Constants.TAG
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(

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
                login()
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            _eventFlow.emit(LoginUiEvent.Login)
        }
    }
}