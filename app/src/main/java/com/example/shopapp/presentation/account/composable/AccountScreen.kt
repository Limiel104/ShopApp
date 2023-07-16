package com.example.shopapp.presentation.account.composable

import android.util.Log
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.account.AccountEvent
import com.example.shopapp.presentation.account.AccountUiEvent
import com.example.shopapp.presentation.account.AccountViewModel
import com.example.shopapp.presentation.common.composable.UserNotLoggedInContent
import com.example.shopapp.util.Constants
import com.example.shopapp.util.Constants.ACCOUNT_SCREEN_LE
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AccountScreen(
    navController: NavController,
    bottomBarHeight: Dp,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val name = viewModel.accountState.value.name
    val isUserLoggedIn = viewModel.accountState.value.isUserLoggedIn

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            Log.i(Constants.TAG, ACCOUNT_SCREEN_LE)
            when(event) {
                is AccountUiEvent.NavigateToLogin -> {
                    navController.navigate(Screen.LoginScreen.route)
                }
                is AccountUiEvent.NavigateToSignup -> {
                    navController.navigate(Screen.SignupScreen.route)
                }
            }
        }
    }

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
            onLogin = { viewModel.onEvent(AccountEvent.OnLogin) },
            onSignup = { viewModel.onEvent(AccountEvent.OnSignup) }
        )
    }
}