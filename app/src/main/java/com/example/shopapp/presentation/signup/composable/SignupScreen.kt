package com.example.shopapp.presentation.signup.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.common.getLastDestination
import com.example.shopapp.presentation.signup.SignupEvent
import com.example.shopapp.presentation.signup.SignupUiEvent
import com.example.shopapp.presentation.signup.SignupViewModel
import com.example.shopapp.util.Constants.SIGNUP_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignupScreen(
    navController: NavController,
    bottomBarHeight: Dp,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val email = viewModel.signupState.value.email
    val emailError = viewModel.signupState.value.emailError
    val password = viewModel.signupState.value.password
    val passwordError = viewModel.signupState.value.passwordError
    val confirmPassword = viewModel.signupState.value.confirmPassword
    val confirmPasswordError = viewModel.signupState.value.confirmPasswordError
    val isLoading = viewModel.signupState.value.isLoading
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            Log.i(TAG,SIGNUP_SCREEN_LE)
            when(event) {
                is SignupUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is SignupUiEvent.Signup -> {
                    val destination = getLastDestination(navController)
                    navController.navigate(destination) {
                        popUpTo(destination) { inclusive = true }
                    }
                }
            }
        }
    }

    SignupContent(
        bottomBarHeight = bottomBarHeight,
        email = email,
        emailError = emailError,
        password = password,
        passwordError = passwordError,
        confirmPassword = confirmPassword,
        confirmPasswordError = confirmPasswordError,
        isLoading = isLoading,
        onEmailChange = { viewModel.onEvent(SignupEvent.EnteredEmail(it)) },
        onPasswordChange = { viewModel.onEvent(SignupEvent.EnteredPassword(it)) },
        onConfirmPasswordChange = { viewModel.onEvent(SignupEvent.EnteredConfirmPassword(it)) },
        onSignup = { viewModel.onEvent(SignupEvent.Signup) }
    )
}