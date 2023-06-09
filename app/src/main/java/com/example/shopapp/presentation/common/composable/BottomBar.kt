package com.example.shopapp.presentation.common.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.shopapp.domain.model.BottomBarItem
import com.example.shopapp.util.Screen

@Composable
fun BottomBar(
    navController: NavController
) {
    BottomBarNavigation(
        items = listOf(
            BottomBarItem(
                name = "Home",
                route = Screen.HomeScreen.route,
                icon = Icons.Outlined.Home
            ),

            BottomBarItem(
                name = "Categories",
                route = Screen.CategoryScreen.route,
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
        navController = navController,
        onItemClick = {
            navController.navigate(it.route)
        }
    )
}