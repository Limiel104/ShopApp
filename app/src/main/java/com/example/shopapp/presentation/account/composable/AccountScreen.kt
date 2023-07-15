package com.example.shopapp.presentation.account.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopapp.presentation.account.AccountViewModel
import com.example.shopapp.presentation.common.composable.UserNotLoggedInContent

@Composable
fun AccountScreen(
    bottomBarHeight: Dp,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val name = viewModel.accountState.value.name
    val isUserLoggedIn = viewModel.accountState.value.isUserLoggedIn

    if(isUserLoggedIn) {
        AccountContent(
            scaffoldState = scaffoldState,
            bottomBarHeight = bottomBarHeight,
            userName = name,
            userClubPoints = 234
        )
    }
    else {
        UserNotLoggedInContent(
            scaffoldState = scaffoldState,
            bottomBarHeight = bottomBarHeight,
            onLogin = {},
            onSignup = {}
        )
    }
}