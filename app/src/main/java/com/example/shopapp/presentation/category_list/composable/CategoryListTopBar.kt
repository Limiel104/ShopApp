package com.example.shopapp.presentation.category_list.composable

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.CATEGORY_LIST_TOP_BAR

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