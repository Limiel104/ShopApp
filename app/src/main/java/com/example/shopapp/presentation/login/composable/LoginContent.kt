package com.example.shopapp.presentation.login.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.R
import com.example.shopapp.presentation.common.composable.ErrorTextFieldItem
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.presentation.common.composable.ShopTextFieldItem
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.LOGIN_BTN
import com.example.shopapp.util.Constants.LOGIN_CPI
import com.example.shopapp.util.Constants.LOGIN_EMAIL_ERROR
import com.example.shopapp.util.Constants.LOGIN_EMAIL_TF
import com.example.shopapp.util.Constants.LOGIN_PASSWORD_ERROR
import com.example.shopapp.util.Constants.LOGIN_PASSWORD_TF
import com.example.shopapp.util.Constants.LOGIN_SIGNUP_BTN

@Composable
fun LoginContent(
    bottomBarHeight: Dp,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onSignup: () -> Unit
) {
    val emailError = ""
    val passwordError = ""
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
            text = stringResource(id = R.string.login),
            color = MaterialTheme.colors.secondary,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold
        )

        Column {
            ShopTextFieldItem(
                text = email,
                label = stringResource(id = R.string.email),
                placeholder = stringResource(id = R.string.email),
                testTag = LOGIN_EMAIL_TF,
                isError = emailError != null,
                onValueChange = { onEmailChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            if(emailError != null) {
                ErrorTextFieldItem(
                    errorMessage = emailError,
                    testTag = LOGIN_EMAIL_ERROR
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ShopTextFieldItem(
                text = password,
                label = stringResource(id = R.string.password),
                placeholder = stringResource(id = R.string.password),
                testTag = LOGIN_PASSWORD_TF,
                isError = passwordError != null,
                onValueChange = { onPasswordChange(it) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            if(passwordError != null) {
                ErrorTextFieldItem(
                    errorMessage = passwordError,
                    testTag = LOGIN_PASSWORD_ERROR
                )
            }
        }

        ShopButtonItem(
            text = stringResource(id = R.string.login),
            testTag = LOGIN_BTN,
            onClick = { onLogin() }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.no_account_text)
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = stringResource(id = R.string.signup),
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable { onSignup() }
                    .testTag(LOGIN_SIGNUP_BTN)
            )
        }
    }

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag(LOGIN_CPI),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun LoginContentPreview() {
    ShopAppTheme() {
        LoginContent(
            bottomBarHeight = 56.dp,
            email = "email@wp.com",
            password = "abcdefg+A",
            onEmailChange = {},
            onPasswordChange = {},
            onLogin = {},
            onSignup = {}
        )
    }
}