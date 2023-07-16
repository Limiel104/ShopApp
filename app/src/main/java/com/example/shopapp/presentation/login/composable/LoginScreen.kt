package com.example.shopapp.presentation.login.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.login.LoginEvent
import com.example.shopapp.presentation.login.LoginViewModel
import com.example.shopapp.util.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    bottomBarHeight: Dp,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val email = viewModel.loginState.value.email
    val password = viewModel.loginState.value.password

    LoginContent(
        bottomBarHeight = bottomBarHeight,
        email = email,
        password = password,
        onEmailChange = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
        onPasswordChange = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
        onLogin = { navController.popBackStack() },
        onSignup = { navController.navigate(Screen.SignupScreen.route) }
    )
}