package com.example.shopapp.presentation.account.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.R
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.presentation.common.Constants.ACCOUNT_TOP_BAR
import com.example.shopapp.presentation.common.Constants.CART_BTN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountTopBar(
    userName: String,
    onClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.hi) + " $userName") },
        actions = {
            IconButton(
                onClick = { onClick() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = CART_BTN
                )
            }
        },
        modifier = Modifier.testTag(ACCOUNT_TOP_BAR)
    )
}

@Preview
@Composable
fun AccountTopBarPreview() {
    ShopAppTheme {
        AccountTopBar(
            userName = "John",
            onClick = {}
        )
    }
}