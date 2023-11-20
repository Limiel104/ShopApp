package com.example.shopapp.presentation.category_list.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.presentation.common.Constants.CART_BTN
import com.example.shopapp.presentation.common.Constants.CATEGORY_LIST_TOP_BAR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListTopBar(
    onClick: () -> Unit
) {
    TopAppBar(
        title = {},
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
        modifier = Modifier.testTag(CATEGORY_LIST_TOP_BAR)
    )
}

@Preview
@Composable
fun CategoryListTopBarPreview() {
    ShopAppTheme {
        CategoryListTopBar(
            onClick = {}
        )
    }
}