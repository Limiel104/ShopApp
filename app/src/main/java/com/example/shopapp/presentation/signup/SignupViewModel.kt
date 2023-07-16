package com.example.shopapp.presentation.signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.util.Constants.SIGNUP_VM
import com.example.shopapp.util.Constants.TAG
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SignupViewModel(
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
                signup()
            }
        }
    }

    private fun signup() {
        viewModelScope.launch {
            _eventFlow.emit(SignupUiEvent.Signup)
        }
    }
}