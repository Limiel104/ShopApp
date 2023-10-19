package com.example.shopapp.presentation.signup.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.presentation.common.composable.ErrorTextFieldItem
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.presentation.common.composable.ShopTextFieldItem
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.SIGNUP_BTN
import com.example.shopapp.util.Constants.SIGNUP_CITY_ERROR
import com.example.shopapp.util.Constants.SIGNUP_CITY_TF
import com.example.shopapp.util.Constants.SIGNUP_COLUMN
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_CONTENT
import com.example.shopapp.util.Constants.SIGNUP_CPI
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_ERROR
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_TF
import com.example.shopapp.util.Constants.SIGNUP_FIRSTNAME_ERROR
import com.example.shopapp.util.Constants.SIGNUP_FIRSTNAME_TF
import com.example.shopapp.util.Constants.SIGNUP_LASTNAME_ERROR
import com.example.shopapp.util.Constants.SIGNUP_LASTNAME_TF
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_STREET_ERROR
import com.example.shopapp.util.Constants.SIGNUP_STREET_TF
import com.example.shopapp.util.Constants.SIGNUP_ZIP_CODE_ERROR
import com.example.shopapp.util.Constants.SIGNUP_ZIP_CODE_TF
import com.example.shopapp.util.Constants.cityEmptyError
import com.example.shopapp.util.Constants.confirmPasswordError
import com.example.shopapp.util.Constants.emailEmptyError
import com.example.shopapp.util.Constants.fieldEmptyError
import com.example.shopapp.util.Constants.passwordEmptyError
import com.example.shopapp.util.Constants.streetEmptyError
import com.example.shopapp.util.Constants.zipCodeEmptyError

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignupContent(
    scaffoldState: ScaffoldState,
    scrollState: ScrollState,
    email: String,
    emailError: String?,
    password: String,
    passwordError: String?,
    confirmPassword: String,
    confirmPasswordError: String?,
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
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onStreetChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onSignup: () -> Unit,
    onGoBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            SignupTopBar(
                onClick = { onGoBack() }
            ) },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(SIGNUP_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
                .verticalScroll(scrollState)
                .testTag(SIGNUP_COLUMN),
            horizontalAlignment = Alignment.Start
        ) {
            ShopTextFieldItem(
                text = email,
                label = stringResource(id = R.string.email),
                placeholder = stringResource(id = R.string.email),
                testTag = SIGNUP_EMAIL_TF,
                isError = emailError != null,
                onValueChange = { onEmailChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            if(emailError != null) {
                ErrorTextFieldItem(
                    errorMessage = emailError,
                    testTag = SIGNUP_EMAIL_ERROR
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            ShopTextFieldItem(
                text = password,
                label = stringResource(id = R.string.password),
                placeholder = stringResource(id = R.string.password),
                testTag = SIGNUP_PASSWORD_TF,
                isError = passwordError != null,
                onValueChange = { onPasswordChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            if(passwordError != null) {
                ErrorTextFieldItem(
                    errorMessage = passwordError,
                    testTag = SIGNUP_PASSWORD_ERROR
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            ShopTextFieldItem(
                text = confirmPassword,
                label = stringResource(id = R.string.confirm_password),
                placeholder = stringResource(id = R.string.confirm_password),
                testTag = SIGNUP_CONFIRM_PASSWORD_TF,
                isError = confirmPasswordError != null,
                onValueChange = { onConfirmPasswordChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            if(confirmPasswordError != null) {
                ErrorTextFieldItem(
                    errorMessage = confirmPasswordError,
                    testTag = SIGNUP_CONFIRM_PASSWORD_ERROR
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            ShopTextFieldItem(
                text = firstName,
                label = stringResource(id = R.string.first_name),
                placeholder = stringResource(id = R.string.first_name),
                testTag = SIGNUP_FIRSTNAME_TF,
                isError = firstNameError != null,
                onValueChange = { onFirstNameChange(it) }
            )

            if(firstNameError != null) {
                ErrorTextFieldItem(
                    errorMessage = firstNameError,
                    testTag = SIGNUP_FIRSTNAME_ERROR
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            ShopTextFieldItem(
                text = lastName,
                label = stringResource(id = R.string.last_name),
                placeholder = stringResource(id = R.string.last_name),
                testTag = SIGNUP_LASTNAME_TF,
                isError = lastNameError != null,
                onValueChange = { onLastNameChange(it) }
            )

            if(lastNameError != null) {
                ErrorTextFieldItem(
                    errorMessage = lastNameError,
                    testTag = SIGNUP_LASTNAME_ERROR
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            ShopTextFieldItem(
                text = street,
                label = stringResource(id = R.string.street),
                placeholder = stringResource(id = R.string.street),
                testTag = SIGNUP_STREET_TF,
                isError = streetError != null,
                onValueChange = { onStreetChange(it) }
            )

            if(streetError != null) {
                ErrorTextFieldItem(
                    errorMessage = streetError,
                    testTag = SIGNUP_STREET_ERROR
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row() {
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    ShopTextFieldItem(
                        text = city,
                        label = stringResource(id = R.string.city),
                        placeholder = stringResource(id = R.string.city),
                        testTag = SIGNUP_CITY_TF,
                        isError = cityError != null,
                        onValueChange = { onCityChange(it) }
                    )

                    if(cityError != null) {
                        ErrorTextFieldItem(
                            errorMessage = cityError,
                            testTag = SIGNUP_CITY_ERROR
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
                        testTag = SIGNUP_ZIP_CODE_TF,
                        isError = zipCodeError != null,
                        onValueChange = { onZipCodeChange(it) }
                    )

                    if(zipCodeError != null) {
                        ErrorTextFieldItem(
                            errorMessage = zipCodeError,
                            testTag = SIGNUP_ZIP_CODE_ERROR
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(5.dp))

            ShopButtonItem(
                text = stringResource(id = R.string.signup),
                testTag = SIGNUP_BTN,
                onClick = { onSignup() }
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag(SIGNUP_CPI),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun SignupContentPreview() {
    ShopAppTheme() {
        SignupContent(
            scaffoldState = rememberScaffoldState(),
            scrollState = rememberScrollState(),
            email = "email@wp.com",
            emailError = null,
            password = "abcdef2+A",
            passwordError = null,
            confirmPassword = "abcdef2+A",
            confirmPasswordError = null,
            firstName = "John",
            firstNameError = null,
            lastName = "Smith",
            lastNameError = null,
            street = "Street 1",
            streetError = null,
            city = "Berlin",
            cityError = null,
            zipCode = "123456",
            zipCodeError = null,
            isLoading = false,
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onFirstNameChange = {},
            onLastNameChange = {},
            onStreetChange = {},
            onCityChange = {},
            onZipCodeChange = {},
            onSignup = {},
            onGoBack = {}
        )
    }
}

@Preview
@Composable
fun SignupContentErrorPreview() {
    ShopAppTheme() {
        SignupContent(
            scaffoldState = rememberScaffoldState(),
            scrollState = rememberScrollState(),
            email = "email@wp.com",
            emailError = emailEmptyError,
            password = "abcdef2+A",
            passwordError = passwordEmptyError,
            confirmPassword = "abcdeff2+A",
            confirmPasswordError = confirmPasswordError,
            firstName = "John",
            firstNameError = fieldEmptyError,
            lastName = "Smith",
            lastNameError = fieldEmptyError,
            street = "Street 1",
            streetError = streetEmptyError,
            city = "Berlin",
            cityError = cityEmptyError,
            zipCode = "123456",
            zipCodeError = zipCodeEmptyError,
            isLoading = false,
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onFirstNameChange = {},
            onLastNameChange = {},
            onStreetChange = {},
            onCityChange = {},
            onZipCodeChange = {},
            onSignup = {},
            onGoBack = {}
        )
    }
}

@Preview
@Composable
fun SignupContentCPIPreview() {
    ShopAppTheme() {
        SignupContent(
            scaffoldState = rememberScaffoldState(),
            scrollState = rememberScrollState(),
            email = "email@wp.com",
            emailError = null,
            password = "abcdef2+A",
            passwordError = null,
            confirmPassword = "abcdeff2+A",
            confirmPasswordError = null,
            firstName = "John",
            firstNameError = null,
            lastName = "Smith",
            lastNameError = null,
            street = "Street 1",
            streetError = null,
            city = "Berlin",
            cityError = null,
            zipCode = "123456",
            zipCodeError = null,
            isLoading = true,
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onFirstNameChange = {},
            onLastNameChange = {},
            onStreetChange = {},
            onCityChange = {},
            onZipCodeChange = {},
            onSignup = {},
            onGoBack = {}
        )
    }
}