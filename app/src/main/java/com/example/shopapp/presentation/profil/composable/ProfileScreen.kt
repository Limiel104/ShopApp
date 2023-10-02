package com.example.shopapp.presentation.profil.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
fun ProfileScreen(
    bottomBarHeight: Dp,
) {
    ProfileContent(
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = bottomBarHeight,
        firstName = "John",
        firstNameError = null,
        lastName = "Smith",
        lastNameError = null,
        street = "Street 1",
        streetError = null,
        city = "Warsaw",
        cityError = null,
        zipCode = "12-345",
        zipCodeError = null,
        onFirstNameChange = {},
        onLastNameChange = {},
        onStreetChange = {},
        onCityChange = {},
        onZipCodeChange = {},
        onGoBack = {},
        onSave = {}
    )
}