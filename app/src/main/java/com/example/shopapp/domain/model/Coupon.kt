package com.example.shopapp.domain.model

import java.util.Date

data class Coupon(
    val userUID: String = "",
    val amount: Int = 0,
    val activationDate: Date = Date()
)