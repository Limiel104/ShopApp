package com.example.shopapp.presentation.navigation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.shopapp.domain.model.BottomBarItem
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun BottomBarNavigationItem(
    item: BottomBarItem
) {
    if(item.badgeCount>0) {
        BadgedBox(
            badge = {
                Badge {
                    Text(item.badgeCount.toString())
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colors.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.name
                )

                Text(
                    text = item.name,
                    fontSize = 10.sp
                )
            }
        }
    }
    else {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.name
            )

            Text(
                text = item.name,
                fontSize = 10.sp
            )
        }
    }
}

@Preview
@Composable
fun BottomBarNavigationItemPreview() {
    ShopAppTheme {
        BottomBarNavigationItem(
            BottomBarItem(
                name = "Favourites",
                route = "",
                icon = Icons.Outlined.FavoriteBorder,
                badgeCount = 0
            )
        )
    }
}

@Preview
@Composable
fun BottomBarNavigationItemCountPreview() {
    ShopAppTheme {
        BottomBarNavigationItem(
            BottomBarItem(
                name = "Favourites",
                route = "",
                icon = Icons.Outlined.FavoriteBorder,
                badgeCount = 10
            )
        )
    }
}