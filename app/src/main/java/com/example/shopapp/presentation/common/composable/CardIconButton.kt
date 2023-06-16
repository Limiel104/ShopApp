package com.example.shopapp.presentation.common.composable

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun CardIconButton(
    icon: ImageVector,
    description: String,
    color: Color = Color.Black,
    outsidePaddingValue: Int = 5,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(35.dp),
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(outsidePaddingValue.dp)
            .size(36.dp)
            .clip(shape = CircleShape)
            .clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            tint = color,
            contentDescription = description,
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Preview
@Composable
fun CardIconButtonPreview() {
    ShopAppTheme {
        CardIconButton(
            icon = Icons.Outlined.ShoppingCart,
            description = "Add to cart",
            onClick = {}
        )
    }
}