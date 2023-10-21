package com.example.shopapp.presentation.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.R
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.LOGIN_BTN
import com.example.shopapp.util.Constants.SIGNUP_BTN
import com.example.shopapp.util.Constants.USER_NOT_LOGGED_IN_CONTENT

@Composable
fun UserNotLoggedInContent(
    onLogin: () -> Unit,
    onSignup: () -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag(USER_NOT_LOGGED_IN_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.not_logged_in),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = stringResource(id = R.string.login_or_signup),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                ShopButtonItem(
                    text = stringResource(id = R.string.login),
                    testTag = LOGIN_BTN,
                    onClick = { onLogin() }
                )

                ShopButtonItem(
                    text = stringResource(id = R.string.signup),
                    testTag = SIGNUP_BTN,
                    onClick = { onSignup() }
                )
            }
        }
    }
}

@Preview
@Composable
fun UserNotLoggedInContentPreview() {
    ShopAppTheme {
        UserNotLoggedInContent(
            onLogin = {},
            onSignup = {}
        )
    }
}