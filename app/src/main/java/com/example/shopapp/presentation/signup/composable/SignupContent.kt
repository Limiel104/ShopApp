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
import com.example.shopapp.util.Constants.SIGNUP_CPI
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_ERROR
import com.example.shopapp.util.Constants.SIGNUP_EMAIL_TF
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_ERROR
import com.example.shopapp.util.Constants.SIGNUP_PASSWORD_TF

@Composable
fun SignupContent(
    bottomBarHeight: Dp,
    email: String,
    password: String,
    confirmPassword: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSignup: () -> Unit
) {
    val emailError = ""
    val passwordError = ""
    val confirmPasswordError = ""
    val isLoading = false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 20.dp)
            .padding(bottom = bottomBarHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = stringResource(id = R.string.create_account),
            fontSize = 32.sp,
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.SemiBold
        )

        Column() {
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
            password = "abcdef2+A",
            confirmPassword = "abcdef2+A",
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onSignup = {}
        )
    }
}