package com.example.shopapp.presentation.favourites.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.presentation.common.composable.IconButtonCard
import com.example.shopapp.presentation.common.composable.ImageItem
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.productImageUrl
import com.example.shopapp.util.Constants.productItemImageHeight
import com.example.shopapp.util.Constants.productItemImageWidth
import com.example.shopapp.util.Constants.productTitle
import com.example.shopapp.util.Constants.productPrice

@Composable
fun FavouriteProductItem(
    title: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(180.dp, 200.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            ImageItem(
                imageUrl = productImageUrl,
                width = productItemImageWidth,
                height = productItemImageHeight
            )

            IconButtonCard(
                icon = Icons.Outlined.ShoppingCart,
                description = CART_BTN,
                onClick = {}
            )
        }

        ProductItemTitle(
            name = title,
            price = productPrice
        )
    }
}

@Preview
@Composable
fun FavouriteProductItemPreview() {
    ShopAppTheme {
        FavouriteProductItem(
            title = productTitle,
            onClick = {}
        )
    }
}