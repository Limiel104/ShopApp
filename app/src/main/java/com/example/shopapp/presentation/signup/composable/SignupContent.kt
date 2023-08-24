package com.example.shopapp.presentation.signup.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.R
import com.example.shopapp.presentation.common.composable.ErrorTextFieldItem
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.presentation.common.composable.ShopTextFieldItem
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.SIGNUP_BTN
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_CONFIRM_PASSWORD_TF
import com.example.shopapp.util.Constants.SIGNUP_CONTENT
import com.example.shopapp.util.Constants.SIGNUP_CPI
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_ERROR
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_TF
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_TF
import com.example.shopapp.util.Constants.confirmPasswordError
import com.example.shopapp.util.Constants.emailEmptyError
import com.example.shopapp.util.Constants.passwordEmptyError

@Composable
fun SignupContent(
    bottomBarHeight: Dp,
    email: String,
    emailError: String?,
    password: String,
    passwordError: String?,
    confirmPassword: String,
    confirmPasswordError: String?,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSignup: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 20.dp)
            .padding(bottom = bottomBarHeight)
            .testTag(SIGNUP_CONTENT),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                fontSize = 32.sp,
                color = MaterialTheme.colors.secondary,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(2F),
            verticalArrangement = Arrangement.SpaceEvenly
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
            bottomBarHeight = 56.dp,
            email = "email@wp.com",
            emailError = null,
            password = "abcdef2+A",
            passwordError = null,
            confirmPassword = "abcdef2+A",
            confirmPasswordError = null,
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
            bottomBarHeight = 56.dp,
            email = "email@wp.com",
            emailError = emailEmptyError,
            password = "abcdef2+A",
            passwordError = passwordEmptyError,
            confirmPassword = "abcdeff2+A",
            confirmPasswordError = confirmPasswordError,
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
            bottomBarHeight = 56.dp,
            email = "email@wp.com",
            emailError = null,
            password = "abcdef2+A",
            passwordError = null,
            confirmPassword = "abcdeff2+A",
            confirmPasswordError = null,
            isLoading = true,
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onSignup = {}
        )
    }
}