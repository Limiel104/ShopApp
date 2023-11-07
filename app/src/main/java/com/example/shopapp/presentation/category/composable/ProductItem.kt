package com.example.shopapp.presentation.category.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.domain.model.Product
import com.example.shopapp.presentation.common.composable.ImageItem
import com.example.shopapp.presentation.common.format.priceToString
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.FAVOURITES_BTN
import com.example.shopapp.util.Constants.productDescription
import com.example.shopapp.util.Constants.productItemImageHeight
import com.example.shopapp.util.Constants.productItemImageWidth

@Composable
fun ProductItem(
    product: Product,
    isButtonEnabled: Boolean,
    onImageClick: () -> Unit,
    onFavourite: () -> Unit
) {
    Column(
        modifier = Modifier
            .testTag(product.title),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageItem(
            imageUrl = product.imageUrl,
            width = productItemImageWidth,
            height = productItemImageHeight,
            onClick = { onImageClick() }
        )

        Row(
            modifier = Modifier
                .width(180.dp)
                .padding(5.dp)
        ) {
            Column(
                modifier = Modifier.weight(1F)
                ) {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = product.priceToString(),
                    fontWeight = FontWeight.Bold,
                )
            }

            Column() {
                IconButton(
                    onClick = { onFavourite() },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = if(product.isInFavourites) Color.Red else Color.Gray
                    ),
                    enabled = isButtonEnabled,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if(product.isInFavourites) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = FAVOURITES_BTN,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProductItemFavouriteFalsePreview() {
    Surface() {
        ShopAppTheme {
            val product = Product(
                id = 1,
                title = "Shirt",
                price = 195.59,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            )

            ProductItem(
                product = product,
                isButtonEnabled = true,
                onImageClick = {},
                onFavourite = {}
            )
        }
    }
}

@Preview
@Composable
fun ProductItemFavouriteFalsePreviewNotEnabled() {
    Surface() {
        ShopAppTheme {
            val product = Product(
                id = 1,
                title = "Shirt",
                price = 195.59,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            )

            ProductItem(
                product = product,
                isButtonEnabled = false,
                onImageClick = {},
                onFavourite = {}
            )
        }
    }
}

@Preview
@Composable
fun ProductItemFavouriteTruePreview() {
    Surface() {
        ShopAppTheme {
            val product = Product(
                id = 1,
                title = "Shirt",
                price = 195.59,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            )

            ProductItem(
                product = product,
                isButtonEnabled = true,
                onImageClick = {},
                onFavourite = {}
            )
        }
    }
}

@Preview
@Composable
fun ProductItemFavouriteTruePreviewNotEnabled() {
    Surface() {
        ShopAppTheme {
            val product = Product(
                id = 1,
                title = "Shirt",
                price = 195.59,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            )

            ProductItem(
                product = product,
                isButtonEnabled = false,
                onImageClick = {},
                onFavourite = {}
            )
        }
    }
}