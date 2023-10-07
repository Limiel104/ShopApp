package com.example.shopapp.presentation.profile.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.presentation.common.composable.ErrorTextFieldItem
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.presentation.common.composable.ShopTextFieldItem
import com.example.shopapp.util.Constants.PROFILE_CITY_ERROR
import com.example.shopapp.util.Constants.PROFILE_CITY_TF
import com.example.shopapp.util.Constants.PROFILE_COLUMN
import com.example.shopapp.util.Constants.PROFILE_CONTENT
import com.example.shopapp.util.Constants.PROFILE_CPI
import com.example.shopapp.util.Constants.PROFILE_FIRSTNAME_ERROR
import com.example.shopapp.util.Constants.PROFILE_FIRSTNAME_TF
import com.example.shopapp.util.Constants.PROFILE_LASTNAME_ERROR
import com.example.shopapp.util.Constants.PROFILE_LASTNAME_TF
import com.example.shopapp.util.Constants.PROFILE_STREET_ERROR
import com.example.shopapp.util.Constants.PROFILE_STREET_TF
import com.example.shopapp.util.Constants.PROFILE_ZIP_CODE_ERROR
import com.example.shopapp.util.Constants.PROFILE_ZIP_CODE_TF
import com.example.shopapp.util.Constants.SAVE_BTN
import com.example.shopapp.util.Constants.bottomBarHeight
import com.example.shopapp.util.Constants.cityEmptyError
import com.example.shopapp.util.Constants.fieldEmptyError
import com.example.shopapp.util.Constants.streetEmptyError
import com.example.shopapp.util.Constants.zipCodeEmptyError

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
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
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
            .padding(bottom = bottomBarHeight)
            .testTag(PROFILE_CONTENT)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .testTag(PROFILE_COLUMN),
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
            ) {
                ShopTextFieldItem(
                    text = firstName,
                    label = stringResource(id = R.string.first_name),
                    placeholder = stringResource(id = R.string.first_name),
                    testTag = PROFILE_FIRSTNAME_TF,
                    isError = firstNameError != null,
                    onValueChange = { onFirstNameChange(it) }
                )

                if(firstNameError != null) {
                    ErrorTextFieldItem(
                        errorMessage = firstNameError,
                        testTag = PROFILE_FIRSTNAME_ERROR
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1F)
            ) {
                ShopTextFieldItem(
                    text = lastName,
                    label = stringResource(id = R.string.last_name),
                    placeholder = stringResource(id = R.string.last_name),
                    testTag = PROFILE_LASTNAME_TF,
                    isError = lastNameError != null,
                    onValueChange = { onLastNameChange(it) }
                )

                if(lastNameError != null) {
                    ErrorTextFieldItem(
                        errorMessage = lastNameError,
                        testTag = PROFILE_LASTNAME_ERROR
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1F)
            ) {
                ShopTextFieldItem(
                    text = street,
                    label = stringResource(id = R.string.street),
                    placeholder = stringResource(id = R.string.street),
                    testTag = PROFILE_STREET_TF,
                    isError = streetError != null,
                    onValueChange = { onStreetChange(it) }
                )

                if(streetError != null) {
                    ErrorTextFieldItem(
                        errorMessage = streetError,
                        testTag = PROFILE_STREET_ERROR
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1F)
            ) {
                Row() {
                    Column(
                        modifier = Modifier.weight(1F)
                    ) {
                        ShopTextFieldItem(
                            text = city,
                            label = stringResource(id = R.string.city),
                            placeholder = stringResource(id = R.string.city),
                            testTag = PROFILE_CITY_TF,
                            isError = cityError != null,
                            onValueChange = { onCityChange(it) }
                        )

                        if(cityError != null) {
                            ErrorTextFieldItem(
                                errorMessage = cityError,
                                testTag = PROFILE_CITY_ERROR
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Column(
                        modifier = Modifier.weight(1F)
                    ) {
                        ShopTextFieldItem(
                            text = zipCode,
                            label = stringResource(id = R.string.zip_code),
                            placeholder = stringResource(id = R.string.zip_code),
                            testTag = PROFILE_ZIP_CODE_TF,
                            isError = zipCodeError != null,
                            onValueChange = { onZipCodeChange(it) }
                        )

                        if(zipCodeError != null) {
                            ErrorTextFieldItem(
                                errorMessage = zipCodeError,
                                testTag = PROFILE_ZIP_CODE_ERROR
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .weight(2F)
            ) {
                ShopButtonItem(
                    text = stringResource(id = R.string.save),
                    testTag = SAVE_BTN,
                    onClick = { onSave() }
                )
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
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = bottomBarHeight.dp,
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
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = bottomBarHeight.dp,
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
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = bottomBarHeight.dp,
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