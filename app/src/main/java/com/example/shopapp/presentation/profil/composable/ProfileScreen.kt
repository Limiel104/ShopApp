package com.example.shopapp.presentation.profil.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.example.shopapp.domain.model.Address
import com.example.shopapp.domain.model.User

@Composable
fun ProfileScreen(
    bottomBarHeight: Dp,
) {
    val user = User(
        userUID = "userUID",
        firstName = "John",
        lastName = "Smith",
        address = Address(
            street = "Street 1",
            city = "Warsaw",
            zipCode = "12-345"
        ),
        points = 0
    )

    ProfileContent(
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = bottomBarHeight,
        user = user,
        firstNameError = null,
        lastNameError = null,
        streetError = null,
        cityError = null,
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