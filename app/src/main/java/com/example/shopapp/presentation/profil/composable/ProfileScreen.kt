package com.example.shopapp.presentation.profil.composable

import android.util.Log
import android.widget.Toast
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.profil.ProfileEvent
import com.example.shopapp.presentation.profil.ProfileUiEvent
import com.example.shopapp.presentation.profil.ProfileViewModel
import com.example.shopapp.util.Constants.PROFILE_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileScreen(
    navController: NavController,
    bottomBarHeight: Dp,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val firstName = viewModel.profileState.value.firstName
    val firstNameError = viewModel.profileState.value.firstNameError
    val lastName = viewModel.profileState.value.lastName
    val lastNameError = viewModel.profileState.value.lastNameError
    val street = viewModel.profileState.value.street
    val streetError = viewModel.profileState.value.streetError
    val city = viewModel.profileState.value.city
    val cityError = viewModel.profileState.value.cityError
    val zipCode = viewModel.profileState.value.zipCode
    val zipCodeError = viewModel.profileState.value.zipCodeError
    val isLoading = viewModel.profileState.value.isLoading
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            Log.i(TAG, PROFILE_SCREEN_LE)
            when(event) {
                is ProfileUiEvent.ShowErrorMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is ProfileUiEvent.Save -> {
                    navController.popBackStack()
                }
            }
        }
    }

    ProfileContent(
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = bottomBarHeight,
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
        onFirstNameChange = { viewModel.onEvent(ProfileEvent.EnteredFirstName(it)) },
        onLastNameChange = { viewModel.onEvent(ProfileEvent.EnteredLastName(it)) },
        onStreetChange = { viewModel.onEvent(ProfileEvent.EnteredStreet(it)) },
        onCityChange = { viewModel.onEvent(ProfileEvent.EnteredCity(it)) },
        onZipCodeChange = { viewModel.onEvent(ProfileEvent.EnteredZipCode(it)) },
        onGoBack = {},
        onSave = { viewModel.onEvent(ProfileEvent.Save) }
    )
}