package com.example.shopapp.presentation.login.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.presentation.common.Constants.LOGIN_BTN
import com.example.shopapp.presentation.common.Constants.LOGIN_COLUMN
import com.example.shopapp.presentation.common.Constants.LOGIN_CONTENT
import com.example.shopapp.presentation.common.Constants.LOGIN_CPI
import com.example.shopapp.presentation.common.Constants.LOGIN_EMAIL_TF
import com.example.shopapp.presentation.common.Constants.LOGIN_PASSWORD_TF
import com.example.shopapp.presentation.common.Constants.LOGIN_SIGNUP_BTN
import com.example.shopapp.presentation.common.Constants.emailEmptyError
import com.example.shopapp.presentation.common.Constants.passwordEmptyError

@Composable
fun LoginContent(
    email: String,
    emailError: String?,
    password: String,
    passwordError: String?,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLogin: () -> Unit,
    onSignup: () -> Unit,
    onGoBack: () -> Unit
) {
    Scaffold(
        topBar = {
            LoginTopBar(
                onClick = { onGoBack() }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .testTag(LOGIN_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .testTag(LOGIN_COLUMN),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1F),
                verticalArrangement = Arrangement.Bottom
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { onEmailChange(it) },
                    label = { Text(text = stringResource(id = R.string.email)) },
                    placeholder = { Text(text = stringResource(id = R.string.email)) },
                    supportingText = {
                        if (emailError != null) {
                            Text(text = emailError)
                        } },
                    isError = emailError != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true,
                    modifier = Modifier.testTag(LOGIN_EMAIL_TF)
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { onPasswordChange(it) },
                    label = { Text(text = stringResource(id = R.string.password)) },
                    placeholder = { Text(text = stringResource(id = R.string.password)) },
                    supportingText = {
                        if (passwordError != null) {
                            Text(text = passwordError)
                        } },
                    isError = passwordError != null,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    singleLine = true,
                    modifier = Modifier.testTag(LOGIN_PASSWORD_TF)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1F),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .testTag(LOGIN_BTN),
                    onClick = { onLogin() }
                ) {
                    Text(text = stringResource(id = R.string.login))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.no_account_text))

                    Spacer(modifier = Modifier.width(5.dp))

                    TextButton(
                        onClick = { onSignup() }
                    ) {
                        Text(
                            text = stringResource(id = R.string.signup),
                            modifier = Modifier.testTag(LOGIN_SIGNUP_BTN)
                        )
                    }
                }
            }
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
            email = "email@wp.com",
            emailError = null,
            password = "abcdef2+A",
            passwordError = null,
            onEmailChange = {},
            onPasswordChange = {},
            isLoading = false,
            onLogin = {},
            onSignup = {},
            onGoBack = {}
        )
    }
}

@Preview
@Composable
fun LoginContentErrorPreview() {
    ShopAppTheme() {
        LoginContent(
            email = "email@wp.com",
            emailError = emailEmptyError,
            password = "abcdef2+A",
            passwordError = passwordEmptyError,
            onEmailChange = {},
            onPasswordChange = {},
            isLoading = false,
            onLogin = {},
            onSignup = {},
            onGoBack = {}
        )
    }
}

@Preview
@Composable
fun LoginContentCPIPreview() {
    ShopAppTheme() {
        LoginContent(
            email = "email@wp.com",
            emailError = null,
            password = "abcdef2+A",
            passwordError = null,
            onEmailChange = {},
            onPasswordChange = {},
            isLoading = true,
            onLogin = {},
            onSignup = {},
            onGoBack = {}
        )
    }
}