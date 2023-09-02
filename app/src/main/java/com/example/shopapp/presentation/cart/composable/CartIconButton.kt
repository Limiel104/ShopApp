package com.example.shopapp.presentation.cart.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CartIconButton(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(16.dp)
            .border(1.dp, Color.DarkGray, RoundedCornerShape(5.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            tint = Color.Gray,
            contentDescription = description,
            modifier = Modifier
                .clickable { onClick() }
        )
    }
}

@Preview
@Composable
fun CartIconButtonPreview() {
    CartIconButton(
        icon = Icons.Sharp.Add,
        description = "Button",
        onClick = {}
    )
}