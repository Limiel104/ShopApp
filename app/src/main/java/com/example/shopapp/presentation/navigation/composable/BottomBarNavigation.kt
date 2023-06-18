package com.example.shopapp.presentation.navigation.composable

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.domain.model.BottomBarItem
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Screen

@Composable
fun BottomBarNavigation(
    items: List<BottomBarItem>,
    navBackStackEntry: State<NavBackStackEntry?>,
    onItemClick: (BottomBarItem) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        items.forEach { item ->
            val selected = item.route == navBackStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
                icon = { BottomBarNavigationItem(item = item) }
            )
        }
    }
}

@Preview
@Composable
fun BottomBarNavigationPreview() {
    ShopAppTheme {
        val state = rememberNavController().currentBackStackEntryAsState()
        BottomBarNavigation(
            items = listOf(
                BottomBarItem(
                    name = stringResource(id = R.string.home),
                    route = Screen.HomeScreen.route,
                    icon = Icons.Outlined.Home,
                    badgeCount = 10
                ),

                BottomBarItem(
                    name = stringResource(id = R.string.categories),
                    route = Screen.CategoryScreen.route,
                    icon = Icons.Outlined.Search
                ),

                BottomBarItem(
                    name = stringResource(id = R.string.favourite),
                    route = Screen.FavouriteScreen.route,
                    icon = Icons.Outlined.FavoriteBorder
                ),

                BottomBarItem(
                    name = stringResource(id = R.string.account),
                    route = Screen.AccountScreen.route,
                    icon = Icons.Outlined.Person
                ),
            ),
            onItemClick = {},
            navBackStackEntry = state
        )
    }
}