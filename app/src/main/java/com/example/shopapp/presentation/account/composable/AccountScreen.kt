package com.example.shopapp.presentation.account.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopapp.presentation.account.AccountViewModel

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val name = viewModel.accountState.value.name

    AccountContent(
        customerName = name,
        customerClubPoints = 234,
        scaffoldState = scaffoldState
    )
}