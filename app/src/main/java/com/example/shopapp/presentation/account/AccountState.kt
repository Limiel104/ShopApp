package com.example.shopapp.presentation.account

import com.example.shopapp.domain.model.Address
import com.example.shopapp.domain.model.Coupon
import com.example.shopapp.domain.model.User
import java.util.Date

data class AccountState (
    val isUserLoggedIn: Boolean = false,
    val user: User = User(
        userUID = "",
        firstName = "",
        lastName = "",
        address = Address("","",""),
        points = 0
    ),
    val isLoading: Boolean = false,
    val isCouponActivated: Boolean = false,
    val coupon: Coupon = Coupon(
        userUID = "",
        amount = 0,
        activationDate = Date()
    )
)