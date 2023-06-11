package com.example.shopapp.presentation.category.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.presentation.common.composable.ImageItem
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun ProductItem(
    isProductInFavourites: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageItem(
            imageUrl = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
            width = 180,
            height = 200
        )

        Column(
            modifier = Modifier
                .width(180.dp)
                .padding(5.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    text = "Shirt with regular line retert ewjwerhwjkehrwk",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(5F)
                )

                Icon(
                    imageVector = if(isProductInFavourites) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    tint = if(isProductInFavourites) Color.Red else Color.Gray,
                    contentDescription = "Add to favourites",
                    modifier = Modifier
                        .weight(1F)
                        .clickable {}
                )
            }

            Row {
                Text(
                    text = "15,59 PLN",
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview
@Composable
fun ProductItemFavouriteFalsePreview() {
    ShopAppTheme {
        ProductItem(
            isProductInFavourites = false,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun ProductItemFavouriteTruePreview() {
    ShopAppTheme {
        ProductItem(
            isProductInFavourites = true,
            onClick = {}
        )
    }
}