package com.example.shopapp.presentation.signup.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.SIGNUP_BTN
import com.example.shopapp.util.Constants.SIGNUP_CITY_TF
import com.example.shopapp.util.Constants.SIGNUP_COLUMN
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_CONTENT
import com.example.shopapp.util.Constants.SIGNUP_CPI
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_TF
import com.example.shopapp.util.Constants.SIGNUP_FIRSTNAME_TF
import com.example.shopapp.util.Constants.SIGNUP_LASTNAME_TF
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_STREET_TF
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
        modifier = Modifier
            .fillMaxSize()
            .testTag(SIGNUP_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .testTag(SIGNUP_COLUMN),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { onEmailChange(it) },
                label = { Text(text = stringResource(id = R.string.email)) },
                placeholder = { Text(text = stringResource(id = R.string.email)
                ) },
                supportingText = {
                    if(emailError != null) {
                        Text(text = emailError)
                    } },
                isError = emailError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                singleLine = true,
                modifier = Modifier.testTag(SIGNUP_EMAIL_TF)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { onPasswordChange(it) },
                label = { Text(text = stringResource(id = R.string.password)) },
                placeholder = { Text(text = stringResource(id = R.string.password)
                ) },
                supportingText = {
                    if(passwordError != null) {
                        Text(text = passwordError)
                    } },
                isError = passwordError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                modifier = Modifier.testTag(SIGNUP_PASSWORD_TF)
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { onConfirmPasswordChange(it) },
                label = { Text(text = stringResource(id = R.string.confirm_password)) },
                placeholder = { Text(text = stringResource(id = R.string.confirm_password)
                ) },
                supportingText = {
                    if(confirmPasswordError != null) {
                        Text(text = confirmPasswordError)
                    } },
                isError = confirmPasswordError != null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                singleLine = true,
                modifier = Modifier.testTag(SIGNUP_CONFIRM_PASSWORD_TF)
            )

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
                modifier = Modifier.testTag(SIGNUP_FIRSTNAME_TF)
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
                modifier = Modifier.testTag(SIGNUP_LASTNAME_TF)
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
                modifier = Modifier.testTag(SIGNUP_STREET_TF)
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
                modifier = Modifier.testTag(SIGNUP_CITY_TF)
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
                modifier = Modifier.testTag(SIGNUP_ZIP_CODE_TF)
            )

            Button(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .testTag(SIGNUP_BTN),
                onClick = { onSignup() }
            ) {
                Text(text =stringResource(id = R.string.signup))
            }
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