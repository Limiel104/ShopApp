package com.example.shopapp.presentation.category.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.presentation.common.Constants.CART_BTN
import com.example.shopapp.presentation.common.Constants.CATEGORY_TOP_BAR
import com.example.shopapp.presentation.common.Constants.SORT_AND_FILTER_BTN

@OptIn(ExperimentalMaterial3Api::class)
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