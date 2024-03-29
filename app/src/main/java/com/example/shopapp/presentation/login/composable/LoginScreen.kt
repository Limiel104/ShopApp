package com.example.shopapp.presentation.login.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.example.shopapp.presentation.common.getLastDestination
import com.example.shopapp.presentation.login.LoginEvent
import com.example.shopapp.presentation.login.LoginUiEvent
import com.example.shopapp.presentation.login.LoginViewModel
import com.example.shopapp.presentation.common.Constants.LOGIN_SCREEN_LE
import com.example.shopapp.presentation.common.Constants.TAG
import com.example.shopapp.presentation.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val email = viewModel.loginState.value.email
    val emailError = viewModel.loginState.value.emailError
    val password = viewModel.loginState.value.password
    val passwordError = viewModel.loginState.value.passwordError
    val isLoading = viewModel.loginState.value.isLoading
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.loginEventChannelFlow.collectLatest { event ->
                Log.i(TAG, LOGIN_SCREEN_LE)
                when(event) {
                    is LoginUiEvent.ShowErrorMessage -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    }
                    is LoginUiEvent.Login -> {
                        val destination = getLastDestination(navController)
                        navController.navigate(destination) {
                            popUpTo(destination) { inclusive = true }
                        }
                    }
                    is LoginUiEvent.NavigateToSignup -> {
                        navController.navigate(Screen.SignupScreen.route)
                    }
                    is LoginUiEvent.NavigateBack -> {
                        navController.popBackStack()
                    }
                }
            }
        }
    }

    LoginContent(
        email = email,
        emailError = emailError,
        password = password,
        passwordError = passwordError,
        onEmailChange = { viewModel.onEvent(LoginEvent.EnteredEmail(it)) },
        onPasswordChange = { viewModel.onEvent(LoginEvent.EnteredPassword(it)) },
        isLoading = isLoading,
        onLogin = { viewModel.onEvent(LoginEvent.Login) },
        onSignup = { viewModel.onEvent(LoginEvent.OnSignupButtonSelected) },
        onGoBack = { viewModel.onEvent(LoginEvent.OnGoBack) }
    )
}