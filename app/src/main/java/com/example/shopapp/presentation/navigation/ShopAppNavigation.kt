package com.example.shopapp.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.shopapp.presentation.common.composable.BottomBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ShopAppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        NavigationGraph(navController = navController)
    }
}