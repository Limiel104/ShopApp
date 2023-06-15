package com.example.shopapp.presentation.navigation.composable

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.util.Screen

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShopAppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentRoute(navBackStackEntry.value)

    Scaffold(
        bottomBar = {
            if (currentRoute != null) {
                if(!currentRoute.contains(Screen.ProductDetailsScreen.route)) {
                    BottomBar(
                        navController = navController,
                        navBackStackEntry = navBackStackEntry
                    )
                }
            }
        }
    ) {
        NavigationGraph(navController = navController)
    }
}

@Composable
fun currentRoute(navBackStackEntry: NavBackStackEntry?): String? {
    return navBackStackEntry?.destination?.route
}