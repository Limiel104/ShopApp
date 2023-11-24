package com.example.shopapp.presentation.product_details.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.presentation.common.Constants.CART_BTN
import com.example.shopapp.presentation.common.Constants.GO_BACK_BTN
import com.example.shopapp.presentation.common.Constants.PRODUCT_DETAILS_TOP_BAR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsTopBar(
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { onNavigateBack() }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = GO_BACK_BTN
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onNavigateToCart() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = CART_BTN
                )
            }
        },
        modifier = Modifier.testTag(PRODUCT_DETAILS_TOP_BAR)
    )
}

@Preview
@Composable
fun FavouriteTopBarPreview() {
    ShopAppTheme {
        ProductDetailsTopBar(
            onNavigateBack = {},
            onNavigateToCart = {}
        )
    }
}