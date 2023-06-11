package com.example.shopapp.presentation.product_details.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.presentation.common.composable.CardIconButton
import com.example.shopapp.presentation.common.composable.ImageItem
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun ProductDetailsImageItem(
    imageUrl: String,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        ImageItem(
            imageUrl = imageUrl
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardIconButton(
                icon = Icons.Default.ArrowBack,
                description = "Go back",
                onClick = {}
            )

            CardIconButton(
                icon = Icons.Default.ShoppingCart,
                description = "Go back",
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
            imageUrl = ""
        )
    }
}