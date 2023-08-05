package com.example.shopapp.presentation.favourites.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.Product
import com.example.shopapp.presentation.common.composable.IconButtonCard
import com.example.shopapp.presentation.common.composable.ImageItem
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.productDescription
import com.example.shopapp.util.Constants.productItemImageHeight
import com.example.shopapp.util.Constants.productItemImageWidth
import com.example.shopapp.R
import com.example.shopapp.util.Constants.ADD_TO_CART_BTN
import com.example.shopapp.util.Constants.DELETE_BTN

@Composable
fun FavouriteProductItem(
    product: Product,
    onClick: () -> Unit,
    onDelete: () ->  Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(bottom = 5.dp)
            .clickable { onClick() },
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

            IconButtonCard(
                icon = Icons.Outlined.Delete,
                description = DELETE_BTN,
                onClick = { onDelete() }
            )
        }

        ProductItemTitle(
            name = product.title,
            price = product.price
        )

        ShopButtonItem(
            text = stringResource(id = R.string.add),
            size = 180,
            testTag = ADD_TO_CART_BTN,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun FavouriteProductItemPreview() {
    val product = Product(
        id = 1,
        title = "Shirt",
        price = "195,59 PLN",
        description = productDescription,
        category = "men's clothing",
        imageUrl = "imageUrl",
        isInFavourites = true
    )

    ShopAppTheme {
        FavouriteProductItem(
            product = product,
            onClick = {},
            onDelete = {}
        )
    }
}