package com.example.shopapp.presentation.cart.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.presentation.common.format.priceToString
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.presentation.common.composable.ImageItem
import com.example.shopapp.util.Constants.MINUS_BTN
import com.example.shopapp.util.Constants.PLUS_BTN
import com.example.shopapp.util.Constants.cartProductItemImageHeight
import com.example.shopapp.util.Constants.cartProductItemImageWidth

@Composable
fun CartProductItem(
    cartProduct: CartProduct,
    onImageClick: () -> Unit,
    onPlus: (Int) -> Unit,
    onMinus: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 10.dp)
            .testTag(cartProduct.title)
    ) {
        ImageItem(
            imageUrl = cartProduct.imageUrl,
            width = cartProductItemImageWidth,
            height = cartProductItemImageHeight,
            onClick = { onImageClick() }
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 10.dp)
                .weight(1f),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = cartProduct.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = cartProduct.priceToString()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CartIconButton(
                icon = Icons.Sharp.Add,
                description = PLUS_BTN,
                onClick = { onPlus(cartProduct.id) }
            )

            Text(
                text = cartProduct.amount.toString(),
                fontWeight = FontWeight.SemiBold
            )

            CartIconButton(
                icon = Icons.Filled.HorizontalRule,
                description = MINUS_BTN,
                onClick = { onMinus(cartProduct.id) }
            )
        }
    }
}

@Preview
@Composable
fun CartProductItemPreview() {
    Surface() {
        CartProductItem(
            cartProduct = CartProduct(
                id = 2,
                title = "title 2",
                price = 53.34,
                imageUrl = "",
                amount = 2
            ),
            onImageClick = {},
            onPlus = {},
            onMinus = {}
        )
    }
}