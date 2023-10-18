package com.example.shopapp.presentation.cart.composable

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.R
import com.example.shopapp.util.Constants.CART_TOP_BAR
import com.example.shopapp.util.Constants.GO_BACK_BTN

@Composable
fun CartTopBar(
    onClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.cart)) },
        navigationIcon = {
            IconButton(
                onClick = { onClick() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = GO_BACK_BTN
                )
            }
        },
        modifier = Modifier.testTag(CART_TOP_BAR)
    )
}

@Preview
@Composable
fun CartTopBarPreview() {
    CartTopBar(
        onClick = {}
    )
}