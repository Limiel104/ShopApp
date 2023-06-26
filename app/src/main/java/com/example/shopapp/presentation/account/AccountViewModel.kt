package com.example.shopapp.presentation.account

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.shopapp.util.Constants.ACCOUNT_VM
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Constants.customerName
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(

): ViewModel() {

    private val _accountState = mutableStateOf(AccountState())
    val accountState: State<AccountState> = _accountState

    init {
        Log.i(TAG, ACCOUNT_VM)

        _accountState.value = accountState.value.copy(
            name = customerName
        )
    }
}