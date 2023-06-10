package com.example.shopapp.presentation.navigation.composable

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShopAppNavigation() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                backStackEntry = backStackEntry
            ) }
    ) {
        NavigationGraph(navController = navController)
    }
}