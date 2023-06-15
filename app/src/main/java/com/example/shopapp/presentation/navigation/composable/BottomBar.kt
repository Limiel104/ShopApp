package com.example.shopapp.presentation.navigation.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.shopapp.domain.model.BottomBarItem
import com.example.shopapp.util.Screen

@Composable
fun BottomBar(
    navController: NavController,
    navBackStackEntry: State<NavBackStackEntry?>
) {
    BottomBarNavigation(
        items = listOf(
            BottomBarItem(
                name = "Home",
                route = Screen.HomeScreen.route,
                icon = Icons.Outlined.Home,
                badgeCount = 10
            ),

            BottomBarItem(
                name = "Categories",
                route = Screen.CategoryListScreen.route,
                icon = Icons.Outlined.Search
            ),

            BottomBarItem(
                name = "Favourites",
                route = Screen.FavouriteScreen.route,
                icon = Icons.Outlined.FavoriteBorder
            ),

            BottomBarItem(
                name = "Account",
                route = Screen.AccountScreen.route,
                icon = Icons.Outlined.Person
            ),
        ),
        navBackStackEntry = navBackStackEntry,
        onItemClick = { navController.navigate(it.route) }
    )
}