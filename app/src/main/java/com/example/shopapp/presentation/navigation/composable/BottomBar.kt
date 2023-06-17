package com.example.shopapp.presentation.navigation.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.shopapp.R
import com.example.shopapp.domain.model.BottomBarItem
import com.example.shopapp.util.Constants.badgeCount
import com.example.shopapp.util.Screen

@Composable
fun BottomBar(
    navController: NavController,
    navBackStackEntry: State<NavBackStackEntry?>
) {
    BottomBarNavigation(
        items = listOf(
            BottomBarItem(
                name = stringResource(id = R.string.home),
                route = Screen.HomeScreen.route,
                icon = Icons.Outlined.Home,
                badgeCount = badgeCount
            ),

            BottomBarItem(
                name = stringResource(id = R.string.categories),
                route = Screen.CategoryListScreen.route,
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
        navBackStackEntry = navBackStackEntry,
        onItemClick = { navController.navigate(it.route) }
    )
}