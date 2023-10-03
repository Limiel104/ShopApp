package com.example.shopapp.presentation.account

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapp.domain.use_case.ShopUseCases
import com.example.shopapp.util.Constants.ACCOUNT_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val shopUseCases: ShopUseCases
): ViewModel() {

    private val _accountState = mutableStateOf(AccountState())
    val accountState: State<AccountState> = _accountState

    private val _eventFlow = MutableSharedFlow<AccountUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        Log.i(TAG, ACCOUNT_VM)

        checkIfUserIsLoggedIn()
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
            is AccountEvent.OnLogout -> {
                viewModelScope.launch {
                    logout()
                }
            }
            is AccountEvent.GoToCart -> {
                viewModelScope.launch {
                    _eventFlow.emit(AccountUiEvent.NavigateToCart)
                }
            }
            AccountEvent.GoToOrders -> {
                viewModelScope.launch {
                    _eventFlow.emit(AccountUiEvent.NavigateToOrders)
                }
            }
            is AccountEvent.GoToProfile -> {
                viewModelScope.launch {
                    val userUID = _accountState.value.user.userUID
                    _eventFlow.emit(AccountUiEvent.NavigateToProfile(userUID))
                }
            }
        }
    }

    fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            val currentUser = shopUseCases.getCurrentUserUseCase()
            _accountState.value = accountState.value.copy(
                isUserLoggedIn = currentUser != null
            )

            if(currentUser != null) {
                getUser(currentUser.uid)
            }
        }
    }

    fun getUser(userUID: String) {
        viewModelScope.launch {
            shopUseCases.getUserUseCase(userUID).collect { response ->
                when(response) {
                    is Resource.Loading -> {
                        Log.i(TAG,"Loading user: ${response.isLoading}")
                        _accountState.value = accountState.value.copy(
                            isLoading = response.isLoading
                        )
                    }
                    is Resource.Success -> {
                        response.data?.let { user ->
                            Log.i(TAG, "User: $user")
                            _accountState.value = accountState.value.copy(
                                user = user[0]
                            )
                        }
                    }
                    is Resource.Error -> {
                        Log.i(TAG, response.message.toString())
                        _eventFlow.emit(AccountUiEvent.ShowErrorMessage(response.message.toString()))
                    }
                }
            }
        }
    }

    fun logout() {
        shopUseCases.logoutUseCase()
        _accountState.value = accountState.value.copy(
            isUserLoggedIn = false
        )
    }
}