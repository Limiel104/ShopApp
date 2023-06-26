package com.example.shopapp.presentation.navigation.composable

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
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
    var bottomBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Scaffold(
        bottomBar = {
            if (currentRoute != null) {
                if(!currentRoute.contains(Screen.ProductDetailsScreen.route)) {
                    BottomBar(
                        navController = navController,
                        navBackStackEntry = navBackStackEntry,
                        modifier = Modifier.onGloballyPositioned {
                            bottomBarHeight = with(density) {
                                it.size.height.toDp()
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavigationGraph(
            navController = navController,
            bottomBarHeight = bottomBarHeight
        )
    }
}

@Composable
fun currentRoute(navBackStackEntry: NavBackStackEntry?): String? {
    return navBackStackEntry?.destination?.route
}