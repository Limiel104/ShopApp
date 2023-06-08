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
import com.example.shopapp.presentation.common.composable.CardIconButton
import com.example.shopapp.presentation.common.composable.ImageItem
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun FavouriteProductItem(
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
                imageUrl = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
                width = 180,
                height = 200
            )

            CardIconButton(
                icon = Icons.Outlined.ShoppingCart,
                description = "Add to cart",
                onClick = {}
            )
        }

        ProductItemTitle(
            name = "Shirt with regular line retert ewjwerhwjkehrwk",
            price = "15,59 PLN"
        )
    }
}

@Preview
@Composable
fun FavouriteProductItemPreview() {
    ShopAppTheme {
        FavouriteProductItem(
            onClick = {}
        )
    }
}