package com.example.shopapp.presentation.account.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable

@Composable
fun AccountScreen() {
    val scaffoldState = rememberScaffoldState()

    AccountContent(
        customerName = "John",
        customerClubPoints = 234,
        scaffoldState = scaffoldState
    )
}