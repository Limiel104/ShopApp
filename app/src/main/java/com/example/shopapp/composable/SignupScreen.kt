package com.example.shopapp.composable

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun SignupScreen() {

    val email = "email@email.com"
    val emailError = ""
    val password = "password"
    val passwordError = ""
    val confirmPassword = "confirmPassword"
    val confirmPasswordError = ""
    val isLoading = false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Create an account",
            fontSize = 32.sp,
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.SemiBold
        )

        Column() {
            ShopTextFieldItem(
                text = email,
                label = "email",
                placeholder = "placeholder",
                testTag = "tag",
                isError = emailError != null,
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            if(emailError != null) {
                ErrorTextFieldItem(
                    errorMessage = emailError,
                    testTag = "tag"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ShopTextFieldItem(
                text = password,
                label = "password",
                placeholder = "placeholder",
                testTag = "tag",
                isError = passwordError != null,
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            if(passwordError != null) {
                ErrorTextFieldItem(
                    errorMessage = passwordError,
                    testTag = "tag"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ShopTextFieldItem(
                text = confirmPassword,
                label = "confirmPassword",
                placeholder = "placeholder",
                testTag = "tag",
                isError = confirmPasswordError != null,
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            if(confirmPasswordError != null) {
                ErrorTextFieldItem(
                    errorMessage = confirmPasswordError,
                    testTag = "tag"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag("tag"),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    ShopAppTheme() {
        SignupScreen()
    }
}