package com.example.shopapp.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun BottomBarItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text
        )

        Text(
            text = text,
            fontSize = 10.sp
        )
    }
}

@Preview
@Composable
fun BottomBarItemPreview() {
    ShopAppTheme {
        BottomBarItem(
            text = "Favourites",
            icon = Icons.Outlined.FavoriteBorder,
            onClick = {}
        )
    }
}