package com.example.shopapp.presentation.profile.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.presentation.common.Constants.PROFILE_CITY_TF
import com.example.shopapp.presentation.common.Constants.PROFILE_COLUMN
import com.example.shopapp.presentation.common.Constants.PROFILE_CONTENT
import com.example.shopapp.presentation.common.Constants.PROFILE_CPI
import com.example.shopapp.presentation.common.Constants.PROFILE_FIRSTNAME_TF
import com.example.shopapp.presentation.common.Constants.PROFILE_LASTNAME_TF
import com.example.shopapp.presentation.common.Constants.PROFILE_STREET_TF
import com.example.shopapp.presentation.common.Constants.PROFILE_ZIP_CODE_TF
import com.example.shopapp.presentation.common.Constants.SAVE_BTN
import com.example.shopapp.presentation.common.Constants.cityEmptyError
import com.example.shopapp.presentation.common.Constants.fieldEmptyError
import com.example.shopapp.presentation.common.Constants.streetEmptyError
import com.example.shopapp.presentation.common.Constants.zipCodeEmptyError

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileContent(
    firstName: String,
    firstNameError: String?,
    lastName: String,
    lastNameError: String?,
    street: String,
    streetError: String?,
    city: String,
    cityError: String?,
    zipCode: String,
    zipCodeError: String?,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onStreetChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onZipCodeChange: (String) -> Unit,
    isLoading: Boolean,
    onGoBack: () -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        topBar = {
            ProfileTopBar(
                onClick = { onGoBack() }
            ) },
        modifier = Modifier
            .fillMaxSize()
            .testTag(PROFILE_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag(PROFILE_COLUMN),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { onFirstNameChange(it) },
                label = { Text(text = stringResource(id = R.string.first_name)) },
                placeholder = { Text(text = stringResource(id = R.string.first_name)
                ) },
                supportingText = {
                    if(firstNameError != null) {
                        Text(text = firstNameError)
                    } },
                isError = firstNameError != null,
                singleLine = true,
                modifier = Modifier.testTag(PROFILE_FIRSTNAME_TF)
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { onLastNameChange(it) },
                label = { Text(text = stringResource(id = R.string.last_name)) },
                placeholder = { Text(text = stringResource(id = R.string.last_name)) },
                supportingText = {
                    if(lastNameError != null) {
                        Text(text = lastNameError)
                    } },
                isError = lastNameError != null,
                singleLine = true,
                modifier = Modifier.testTag(PROFILE_LASTNAME_TF)
            )

            OutlinedTextField(
                value = street,
                onValueChange = { onStreetChange(it) },
                label = { Text(text = stringResource(id = R.string.street)) },
                placeholder = { Text(text = stringResource(id = R.string.street)) },
                supportingText = {
                    if(streetError != null) {
                        Text(text = streetError)
                    } },
                isError = streetError != null,
                singleLine = true,
                modifier = Modifier.testTag(PROFILE_STREET_TF)
            )

            OutlinedTextField(
                value = city,
                onValueChange = { onCityChange(it) },
                label = { Text(text = stringResource(id = R.string.city)) },
                placeholder = { Text(text = stringResource(id = R.string.city)) },
                supportingText = {
                    if(cityError != null) {
                        Text(text = cityError)
                    } },
                isError = cityError != null,
                singleLine = true,
                modifier = Modifier.testTag(PROFILE_CITY_TF)
            )

            OutlinedTextField(
                value = zipCode,
                onValueChange = { onZipCodeChange(it) },
                label = { Text(text = stringResource(id = R.string.zip_code)) },
                placeholder = { Text(text = stringResource(id = R.string.zip_code)) },
                supportingText = {
                    if(zipCodeError != null) {
                        Text(text = zipCodeError)
                    } },
                isError = zipCodeError != null,
                singleLine = true,
                modifier = Modifier.testTag(PROFILE_ZIP_CODE_TF)
            )

            Button(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .testTag(SAVE_BTN),
                onClick = { onSave() }
            ) {
                Text(text =stringResource(id = R.string.save))
            }
        }
    }

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag(PROFILE_CPI),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun ProfileContentPreview() {
    ProfileContent(
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
        isLoading = false,
        onGoBack = {},
        onSave = {}
    )
}

@Preview
@Composable
fun ProfileContentPreviewErrors() {
    ProfileContent(
        firstName = "",
        firstNameError = fieldEmptyError,
        lastName = "",
        lastNameError = fieldEmptyError,
        street = "",
        streetError = streetEmptyError,
        city = "",
        cityError = cityEmptyError,
        zipCode = "",
        zipCodeError = zipCodeEmptyError,
        onFirstNameChange = {},
        onLastNameChange = {},
        onStreetChange = {},
        onCityChange = {},
        onZipCodeChange = {},
        isLoading = false,
        onGoBack = {},
        onSave = {}
    )
}

@Preview
@Composable
fun ProfileContentCPIPreview() {
    ProfileContent(
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
        isLoading = true,
        onGoBack = {},
        onSave = {}
    )
}