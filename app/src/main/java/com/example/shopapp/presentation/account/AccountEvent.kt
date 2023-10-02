package com.example.shopapp.presentation.account

sealed class AccountEvent {
    object OnLogin: AccountEvent()
    object OnSignup: AccountEvent()
    object OnLogout: AccountEvent()
    object GoToCart: AccountEvent()
    object GoToOrders: AccountEvent()
    object GoToProfile: AccountEvent()
}