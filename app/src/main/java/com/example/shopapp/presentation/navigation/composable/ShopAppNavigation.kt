package com.example.shopapp.presentation.navigation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.R
import com.example.shopapp.domain.model.BottomNavigationItem
import com.example.shopapp.presentation.navigation.Screen

@Composable
fun ShopAppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentRoute(navBackStackEntry.value)

    val items = listOf(
        BottomNavigationItem(
            label = stringResource(id = R.string.home),
            route = Screen.HomeScreen.route,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),

        BottomNavigationItem(
            label = stringResource(id = R.string.categories),
            route = Screen.CategoryListScreen.route,
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search
        ),

        BottomNavigationItem(
            label = stringResource(id = R.string.favourite),
            route = Screen.FavouriteScreen.route,
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder
        ),

        BottomNavigationItem(
            label = stringResource(id = R.string.account),
            route = Screen.AccountScreen.route,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        ),
    )

    Scaffold(
        bottomBar = {
            if (currentRoute != null) {
                if(!currentRoute.contains(Screen.ProductDetailsScreen.route)) {
                    NavigationBar() {
                        items.forEachIndexed { index, item ->
                            val isCategoryScreenCurrentlyDisplayed = navBackStackEntry.value?.destination?.route!!.contains(
                                Screen.CategoryScreen.route)
                            val selected =
                                if(isCategoryScreenCurrentlyDisplayed) item.route == Screen.CategoryListScreen.route
                                else item.route == navBackStackEntry.value?.destination?.route

                            NavigationBarItem(
                                selected = selected,
                                onClick = { navController.navigate(item.route) },
                                label = {
                                    Text(
                                        text = item.label,
                                        softWrap = false
                                    ) },
                                icon = {
                                    Icon(
                                        imageVector = if(selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = "Bottom Nav Item ${item.label}"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            NavigationGraph(
                navController = navController
            )
        }
    }
}

@Composable
fun currentRoute(navBackStackEntry: NavBackStackEntry?): String? {
    return navBackStackEntry?.destination?.route
}