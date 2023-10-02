package com.example.shopapp.presentation.account

import com.example.shopapp.domain.model.Address
import com.example.shopapp.domain.model.User

data class AccountState (
    val isUserLoggedIn: Boolean = false,
    val user: User = User(
        userUID = "",
        firstName = "",
        lastName = "",
        address = Address("","",""),
        points = 0
    ),
    val isLoading: Boolean = false
)