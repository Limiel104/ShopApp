package com.example.shopapp.domain.model

data class User(
    val userUID: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val address: Address = Address("","",""),
    val points: Int = 0
)