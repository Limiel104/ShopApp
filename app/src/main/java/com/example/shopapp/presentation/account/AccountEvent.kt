package com.example.shopapp.presentation.account

sealed class AccountEvent {
    object OnLogin: AccountEvent()
    object OnSignup: AccountEvent()
    object OnLogout: AccountEvent()
}