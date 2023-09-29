package com.example.shopapp.presentation.signup.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.presentation.account.composable.SignupTopBar
import com.example.shopapp.presentation.common.composable.ErrorTextFieldItem
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.presentation.common.composable.ShopTextFieldItem
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.SIGNUP_BTN
import com.example.shopapp.util.Constants.SIGNUP_CITY_ERROR
import com.example.shopapp.util.Constants.SIGNUP_CITY_TF
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_CONTENT
import com.example.shopapp.util.Constants.SIGNUP_CPI
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_ERROR
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_TF
import com.example.shopapp.util.Constants.SIGNUP_NAME_ERROR
import com.example.shopapp.util.Constants.SIGNUP_NAME_TF
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_STREET_ERROR
import com.example.shopapp.util.Constants.SIGNUP_STREET_TF
import com.example.shopapp.util.Constants.SIGNUP_ZIP_CODE_ERROR
import com.example.shopapp.util.Constants.SIGNUP_ZIP_CODE_TF
import com.example.shopapp.util.Constants.cityError
import com.example.shopapp.util.Constants.confirmPasswordError
import com.example.shopapp.util.Constants.emailEmptyError
import com.example.shopapp.util.Constants.nameError
import com.example.shopapp.util.Constants.passwordEmptyError
import com.example.shopapp.util.Constants.streetError
import com.example.shopapp.util.Constants.zipCodeError

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SignupContent(
    scaffoldState: ScaffoldState,
    scrollState: ScrollState,
    bottomBarHeight: Dp,
    email: String,
    emailError: String?,
    password: String,
    passwordError: String?,
    confirmPassword: String,
    confirmPasswordError: String?,
    name: String,
    nameError: String?,
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
    onSignup: () -> Unit
) {
    Scaffold(
        topBar = { SignupTopBar() },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
            .padding(bottom = bottomBarHeight)
            .testTag(SIGNUP_CONTENT)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
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

            Spacer(modifier = Modifier.height(10.dp))

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

            Spacer(modifier = Modifier.height(10.dp))

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

            Spacer(modifier = Modifier.height(10.dp))

            ShopTextFieldItem(
                text = name,
                label = stringResource(id = R.string.name),
                placeholder = stringResource(id = R.string.name),
                testTag = SIGNUP_NAME_TF,
                isError = nameError != null,
                onValueChange = {}
            )

            if(nameError != null) {
                ErrorTextFieldItem(
                    errorMessage = nameError,
                    testTag = SIGNUP_NAME_ERROR
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ShopTextFieldItem(
                text = street,
                label = stringResource(id = R.string.street),
                placeholder = stringResource(id = R.string.street),
                testTag = SIGNUP_STREET_TF,
                isError = streetError != null,
                onValueChange = {}
            )

            if(streetError != null) {
                ErrorTextFieldItem(
                    errorMessage = streetError,
                    testTag = SIGNUP_STREET_ERROR
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

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
                        onValueChange = {}
                    )

                    if(cityError != null) {
                        ErrorTextFieldItem(
                            errorMessage = cityError,
                            testTag = SIGNUP_CITY_ERROR
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    ShopTextFieldItem(
                        text = zipCode,
                        label = stringResource(id = R.string.zip_code),
                        placeholder = stringResource(id = R.string.zip_code),
                        testTag = SIGNUP_ZIP_CODE_TF,
                        isError = zipCodeError != null,
                        onValueChange = {}
                    )

                    if(zipCodeError != null) {
                        ErrorTextFieldItem(
                            errorMessage = zipCodeError,
                            testTag = SIGNUP_ZIP_CODE_ERROR
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            ShopButtonItem(
                text = stringResource(id = R.string.signup),
                testTag = SIGNUP_BTN,
                onClick = { onSignup() }
            )
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
            bottomBarHeight = 56.dp,
            email = "email@wp.com",
            emailError = null,
            password = "abcdef2+A",
            passwordError = null,
            confirmPassword = "abcdef2+A",
            confirmPasswordError = null,
            name = "John Smith",
            nameError = null,
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
            onSignup = {}
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
            bottomBarHeight = 56.dp,
            email = "email@wp.com",
            emailError = emailEmptyError,
            password = "abcdef2+A",
            passwordError = passwordEmptyError,
            confirmPassword = "abcdeff2+A",
            confirmPasswordError = confirmPasswordError,
            name = "3453",
            nameError = nameError,
            street = "Street 1",
            streetError = streetError,
            city = "Berlin",
            cityError = cityError,
            zipCode = "123456",
            zipCodeError = zipCodeError,
            isLoading = false,
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onSignup = {}
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
            bottomBarHeight = 56.dp,
            email = "email@wp.com",
            emailError = null,
            password = "abcdef2+A",
            passwordError = null,
            confirmPassword = "abcdeff2+A",
            confirmPasswordError = null,
            name = "John Smith",
            nameError = null,
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
            onSignup = {}
        )
    }
}