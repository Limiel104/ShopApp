package com.example.shopapp.presentation.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HorizontalRule
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.presentation.cart.composable.CartIconButton
import com.example.shopapp.presentation.common.composable.ImageItem
import com.example.shopapp.util.Constants.cartProductItemImageHeight
import com.example.shopapp.util.Constants.cartProductItemImageWidth

@Composable
fun CartItem(
    cartProduct: CartProduct,
    onImageClick: () -> Unit,
    onPlus: () -> Unit,
    onMinus: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .height(IntrinsicSize.Max)
            .padding(bottom = 15.dp)
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
                text = cartProduct.price
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
                onClick = { onPlus() }
            )

            Text(
                text = cartProduct.amount.toString(),
                fontWeight = FontWeight.SemiBold
            )

            CartIconButton(
                icon = Icons.Filled.HorizontalRule,
                onClick = { onMinus() }
            )
        }
    }
}

@Preview
@Composable
fun CartItemPreview() {
    CartItem(
        cartProduct = CartProduct(
            id = 2,
            title = "title 2",
            price = "53,34 PLN",
            imageUrl = "",
            amount = 2
        ),
        onImageClick = {},
        onPlus = {},
        onMinus = {}
    )
}