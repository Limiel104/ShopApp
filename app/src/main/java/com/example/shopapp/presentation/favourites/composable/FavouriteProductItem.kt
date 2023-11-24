package com.example.shopapp.presentation.favourites.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.domain.model.Product
import com.example.shopapp.presentation.common.composable.ImageItem
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.presentation.common.Constants.productDescription
import com.example.shopapp.presentation.common.Constants.productItemImageHeight
import com.example.shopapp.presentation.common.Constants.productItemImageWidth
import com.example.shopapp.R
import com.example.shopapp.presentation.common.Constants.ADD_TO_CART_BTN
import com.example.shopapp.presentation.common.Constants.DELETE_BTN
import com.example.shopapp.presentation.common.Constants.PRODUCT_ITEM_TITLE

@Composable
fun FavouriteProductItem(
    product: Product,
    onClick: () -> Unit,
    onDelete: () ->  Unit,
    onAddToCart: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(bottom = 5.dp)
            .clickable { onClick() }
            .testTag(product.title),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(180.dp, 200.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            ImageItem(
                imageUrl = product.imageUrl,
                width = productItemImageWidth,
                height = productItemImageHeight,
                onClick = { onClick() }
            )

            OutlinedIconButton(
                onClick = { onDelete() },
                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.onSecondary)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = DELETE_BTN + " ${product.title}"
                )
            }
        }

        Column(
            modifier = Modifier
                .width(180.dp)
                .padding(5.dp)
                .testTag(PRODUCT_ITEM_TITLE + " ${product.title}"),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = product.title,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = product.priceToString(),
                fontWeight = FontWeight.Bold
            )
        }

        FilledTonalButton(
            modifier = Modifier
                .testTag(ADD_TO_CART_BTN + " ${product.title}"),
            onClick = { onAddToCart(product.id) }
        ) {
            Text(text = stringResource(id = R.string.add_to_cart))
        }
    }
}

@Preview
@Composable
fun FavouriteProductItemPreview() {
    val product = Product(
        id = 1,
        title = "Shirt",
        price = 195.59,
        description = productDescription,
        category = "men's clothing",
        imageUrl = "imageUrl",
        isInFavourites = true
    )

    Surface() {
        ShopAppTheme {
            FavouriteProductItem(
                product = product,
                onClick = {},
                onDelete = {},
                onAddToCart = {}
            )
        }
    }
}