package com.example.shopapp.presentation.account

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.util.Constants.ACCOUNT_VM
import com.example.shopapp.util.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(

): ViewModel() {

    private val _accountState = mutableStateOf(AccountState())
    val accountState: State<AccountState> = _accountState

    private val _eventFlow = MutableSharedFlow<AccountUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, ACCOUNT_VM)

        _accountState.value = accountState.value.copy(
            name = "John"
        )
    }

    fun onEvent(event: AccountEvent) {
        when(event) {
            is AccountEvent.OnLogin -> {
                viewModelScope.launch {
                    _eventFlow.emit(AccountUiEvent.NavigateToLogin)
                }
            }
            is AccountEvent.OnSignup -> {
                viewModelScope.launch {
                    _eventFlow.emit(AccountUiEvent.NavigateToSignup)
                }
            }
        }
    }
}