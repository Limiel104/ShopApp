package com.example.shopapp.presentation.product_details.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.R
import com.example.shopapp.domain.model.Product
import com.example.shopapp.presentation.common.format.priceToString
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.FAVOURITES_BTN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_ADD_TO_CART_BTN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_BOTTOM_SHEET
import com.example.shopapp.util.Constants.productDescription

@Composable
fun ProductDetailsBottomSheet(
    product: Product,
    isProductInFavourites: Boolean,
    onAddToCart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .testTag(PRODUCT_DETAILS_BOTTOM_SHEET)
    ) {
        Text(
            text = product.title,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.priceToString(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp
            )

            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = if(isProductInFavourites) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = FAVOURITES_BTN,
                    tint = if(isProductInFavourites) Color.Red else Color.Gray
                )
            }
        }

        Text(
            text = stringResource(id = R.string.description),
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 5.dp)
        )

        Text(
            text = product.description,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(top = 5.dp)
                .padding(bottom = 15.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .testTag(PRODUCT_DETAILS_ADD_TO_CART_BTN),
                onClick = { onAddToCart() }
            ) {
                Text(text = stringResource(id = R.string.add_to_cart))
            }
        }
    }
}

@Preview
@Composable
fun ProductDetailsBottomSheetPreview() {
    Surface() {
        ShopAppTheme {
            val product = Product(
                id = 1,
                title = "Shirt",
                price = 195.59,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "",
                isInFavourites = true
            )

            ProductDetailsBottomSheet(
                product = product,
                isProductInFavourites = true,
                onAddToCart = {}
            )
        }
    }
}

@Preview
@Composable
fun ProductDetailsBottomSheetFalsePreview() {
    Surface() {
        ShopAppTheme {
            val product = Product(
                id = 1,
                title = "Shirt",
                price = 195.59,
                description = productDescription,
                category = "men's clothing",
                imageUrl = "",
                isInFavourites = false
            )

            ProductDetailsBottomSheet(
                product = product,
                isProductInFavourites = false,
                onAddToCart = {}
            )
        }
    }
}