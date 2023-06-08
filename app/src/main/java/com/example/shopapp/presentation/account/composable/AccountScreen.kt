package com.example.shopapp.presentation.account.composable

import androidx.compose.runtime.Composable

@Composable
fun AccountScreen() {
    AccountContent(
        customerName = "John",
        customerClubPoints = 234
    )
}