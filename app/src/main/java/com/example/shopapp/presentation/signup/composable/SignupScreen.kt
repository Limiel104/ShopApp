package com.example.shopapp.presentation.signup.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
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
    viewModel: SignupViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    val email = viewModel.signupState.value.email
    val emailError = viewModel.signupState.value.emailError
    val password = viewModel.signupState.value.password
    val passwordError = viewModel.signupState.value.passwordError
    val confirmPassword = viewModel.signupState.value.confirmPassword
    val confirmPasswordError = viewModel.signupState.value.confirmPasswordError
    val firstName = viewModel.signupState.value.firstName
    val firstNameError = viewModel.signupState.value.firstNameError
    val lastName = viewModel.signupState.value.lastName
    val lastNameError = viewModel.signupState.value.lastNameError
    val street = viewModel.signupState.value.street
    val streetError = viewModel.signupState.value.streetError
    val city = viewModel.signupState.value.city
    val cityError = viewModel.signupState.value.cityError
    val zipCode = viewModel.signupState.value.zipCode
    val zipCodeError = viewModel.signupState.value.zipCodeError
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
        scrollState  = scrollState,
        email = email,
        emailError = emailError,
        password = password,
        passwordError = passwordError,
        confirmPassword = confirmPassword,
        confirmPasswordError = confirmPasswordError,
        firstName = firstName,
        firstNameError = firstNameError,
        lastName = lastName,
        lastNameError = lastNameError,
        street = street,
        streetError = streetError,
        city = city,
        cityError = cityError,
        zipCode = zipCode,
        zipCodeError = zipCodeError,
        isLoading = isLoading,
        onEmailChange = { viewModel.onEvent(SignupEvent.EnteredEmail(it)) },
        onPasswordChange = { viewModel.onEvent(SignupEvent.EnteredPassword(it)) },
        onConfirmPasswordChange = { viewModel.onEvent(SignupEvent.EnteredConfirmPassword(it)) },
        onFirstNameChange = { viewModel.onEvent(SignupEvent.EnteredFirstName(it)) },
        onLastNameChange = { viewModel.onEvent(SignupEvent.EnteredLastName(it)) },
        onStreetChange = { viewModel.onEvent(SignupEvent.EnteredStreet(it)) },
        onCityChange = { viewModel.onEvent(SignupEvent.EnteredCity(it)) },
        onZipCodeChange = { viewModel.onEvent(SignupEvent.EnteredZipCode(it)) },
        onSignup = { viewModel.onEvent(SignupEvent.Signup) },
        onGoBack = {}
    )
}