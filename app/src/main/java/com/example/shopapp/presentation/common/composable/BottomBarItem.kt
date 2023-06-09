package com.example.shopapp.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
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
    name: String,
    icon: ImageVector,
    badgeCount: Int
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(badgeCount>0) {
            BadgedBox(
                badge = {
                    Badge {
                        Text(badgeCount.toString())
                    }
                }
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = name
                )

                Text(
                    text = name,
                    fontSize = 10.sp
                )
            }
        }
        else {
            Icon(
                imageVector = icon,
                contentDescription = name
            )

            Text(
                text = name,
                fontSize = 10.sp
            )
        }
    }
}

@Preview
@Composable
fun BottomBarItemPreview() {
    ShopAppTheme {
        BottomBarItem(
            name = "Favourites",
            icon = Icons.Outlined.FavoriteBorder,
            badgeCount = 0
        )
    }
}

@Preview
@Composable
fun BottomBarItemCountPreview() {
    ShopAppTheme {
        BottomBarItem(
            name = "Favourites",
            icon = Icons.Outlined.FavoriteBorder,
            badgeCount = 10
        )
    }
}