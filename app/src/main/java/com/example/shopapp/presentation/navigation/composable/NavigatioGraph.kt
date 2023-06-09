package com.example.shopapp.presentation.navigation.composable

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopapp.presentation.account.composable.AccountScreen
import com.example.shopapp.presentation.category.composable.CategoryScreen
import com.example.shopapp.presentation.category_list.composable.CategoryListScreen
import com.example.shopapp.presentation.favourites.composable.FavouritesScreen
import com.example.shopapp.presentation.home.composable.HomeScreen
import com.example.shopapp.presentation.login.composable.LoginScreen
import com.example.shopapp.presentation.product_details.composable.ProductDetailsScreen
import com.example.shopapp.presentation.signup.composable.SignupScreen
import com.example.shopapp.util.Screen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(
                onNavigateToCategory = { navController.navigate(Screen.CategoryScreen.route) }
            )
        }
        composable(
            route = Screen.FavouriteScreen.route
        ) {
            FavouritesScreen(
                onNavigateToProductDetails = { navController.navigate(Screen.ProductDetailsScreen.route) }
            )
        }
        composable(
            route = Screen.CategoryListScreen.route
        ) {
            CategoryListScreen(
                onNavigateToCategory = { navController.navigate(Screen.CategoryScreen.route) }
            )
        }
        composable(
            route = Screen.CategoryScreen.route
        ) {
            CategoryScreen(
                onNavigateToProductDetails = { navController.navigate(Screen.ProductDetailsScreen.route) }
            )
        }
        composable(
            route = Screen.AccountScreen.route
        ) {
            AccountScreen()
        }
        composable(
            route = Screen.ProductDetailsScreen.route
        ) {
            ProductDetailsScreen()
        }
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen()
        }
        composable(
            route = Screen.SignupScreen.route
        ) {
            SignupScreen()
        }
    }
}