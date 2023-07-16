package com.example.shopapp.presentation.login.composable

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.common.getLastDestination
import com.example.shopapp.presentation.login.LoginEvent
import com.example.shopapp.presentation.login.LoginUiEvent
import com.example.shopapp.presentation.login.LoginViewModel
import com.example.shopapp.util.Constants
import com.example.shopapp.util.Constants.LOGIN_SCREEN_LE
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    bottomBarHeight: Dp,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val email = viewModel.loginState.value.email
    val password = viewModel.loginState.value.password

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            Log.i(Constants.TAG, LOGIN_SCREEN_LE)
            when(event) {
                is LoginUiEvent.ShowErrorMessage -> {}
                is LoginUiEvent.Login -> {
                    val destination = getLastDestination(navController)
                    navController.popBackStack(destination,false)
                }
                is LoginUiEvent.NavigateToSignup -> {
                    navController.navigate(Screen.SignupScreen.route)
                }
            }
        }
    }

    LoginContent(
        bottomBarHeight = bottomBarHeight,
        email = email,
        password = password,
        onEmailChange = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
        onPasswordChange = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
        onLogin = { viewModel.onEvent(LoginEvent.Login) },
        onSignup = { viewModel.onEvent(LoginEvent.OnSignupButtonSelected) }
    )
}