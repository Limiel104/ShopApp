package com.example.shopapp.presentation.category.composable

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.CATEGORY_TOP_BAR
import com.example.shopapp.util.Constants.SORT_AND_FILTER_BTN

@Composable
fun CategoryTopBar(
    categoryName: String,
    onSortAndFilterSelected: () -> Unit,
    onCartSelected: () -> Unit
) {
    TopAppBar(
        title = { Text(text = categoryName) },
        actions = {
            IconButton(
                onClick = { onSortAndFilterSelected() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Sort,
                    contentDescription = SORT_AND_FILTER_BTN
                )
            }

            IconButton(
                onClick = { onCartSelected() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = CART_BTN
                )
            }
        },
        modifier = Modifier.testTag(CATEGORY_TOP_BAR)
    )
}

@Preview
@Composable
fun CategoryTopBarPreview() {
    ShopAppTheme {
        CategoryTopBar(
            categoryName = "men's clothing",
            onSortAndFilterSelected = {},
            onCartSelected = {}
        )
    }
}