package com.example.shopapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun BottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        BottomBarItem(
            text = "Home",
            icon = Icons.Outlined.Home,
            onClick = {}
        )

        BottomBarItem(
            text = "Categories",
            icon = Icons.Outlined.Search,
            onClick = {}
        )

        BottomBarItem(
            text = "Favourites",
            icon = Icons.Outlined.FavoriteBorder,
            onClick = {}
        )

        BottomBarItem(
            text = "Account",
            icon = Icons.Outlined.Person,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    ShopAppTheme {
        BottomBar()
    }
}