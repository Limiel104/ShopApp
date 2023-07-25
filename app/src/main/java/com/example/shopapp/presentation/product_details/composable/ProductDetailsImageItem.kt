package com.example.shopapp.presentation.product_details.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.presentation.common.composable.IconButtonCard
import com.example.shopapp.presentation.common.composable.ImageItem
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.ADD_TO_CART_BTN
import com.example.shopapp.util.Constants.GO_BACK_BTN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_IMAGE_ITEM

@Composable
fun ProductDetailsImageItem(
    imageUrl: String,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag(PRODUCT_DETAILS_IMAGE_ITEM)
    ) {
        ImageItem(
            imageUrl = imageUrl
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButtonCard(
                icon = Icons.Default.ArrowBack,
                description = GO_BACK_BTN,
                onClick = { onNavigateBack() }
            )

            IconButtonCard(
                icon = Icons.Default.ShoppingCart,
                description = ADD_TO_CART_BTN,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
fun ProductDetailsImageItemPreview() {
    ShopAppTheme() {
        ProductDetailsImageItem(
            imageUrl = "",
            onNavigateBack = {}
        )
    }
}