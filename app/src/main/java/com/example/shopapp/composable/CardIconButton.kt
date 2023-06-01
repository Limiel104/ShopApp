package com.example.shopapp.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun CardIconButton() {
    Card(
        shape = RoundedCornerShape(35.dp),
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(5.dp)
            .size(35.dp)
            .clip(shape = CircleShape)
            .clickable {}
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            tint = Color.Black,
            contentDescription = "Add to cart",
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

@Preview
@Composable
fun CardIconButtonPreview() {
    ShopAppTheme {
        CardIconButton()
    }
}