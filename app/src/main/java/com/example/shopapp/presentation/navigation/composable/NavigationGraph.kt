package com.example.shopapp.presentation.navigation.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shopapp.presentation.account.composable.AccountScreen
import com.example.shopapp.presentation.category.composable.CategoryScreen
import com.example.shopapp.presentation.category_list.composable.CategoryListScreen
import com.example.shopapp.presentation.favourites.composable.FavouriteScreen
import com.example.shopapp.presentation.home.composable.HomeScreen
import com.example.shopapp.presentation.login.composable.LoginScreen
import com.example.shopapp.presentation.product_details.composable.ProductDetailsScreen
import com.example.shopapp.presentation.signup.composable.SignupScreen
import com.example.shopapp.util.Constants.categoryId
import com.example.shopapp.util.Constants.productId
import com.example.shopapp.util.Screen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    bottomBarHeight: Dp
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(
            route = Screen.HomeScreen.route
        ) {
            HomeScreen(
                navController = navController,
                bottomBarHeight = bottomBarHeight
            )
        }
        composable(
            route = Screen.FavouriteScreen.route
        ) {
            FavouriteScreen(
                navController = navController,
                bottomBarHeight = bottomBarHeight
            )
        }
        composable(
            route = Screen.CategoryListScreen.route
        ) {
            CategoryListScreen(
                navController = navController,
                bottomBarHeight = bottomBarHeight
            )
        }
        composable(
            route = Screen.CategoryScreen.route + categoryId + "={$categoryId}",
            arguments = listOf(
                navArgument(
                    name = categoryId
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            CategoryScreen(
                navController = navController,
                bottomBarHeight = bottomBarHeight
            )
        }
        composable(
            route = Screen.AccountScreen.route
        ) {
            AccountScreen(bottomBarHeight = bottomBarHeight)
        }
        composable(
            route = Screen.ProductDetailsScreen.route + productId + "={$productId}",
            arguments = listOf(
                navArgument(
                    name = productId
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            ProductDetailsScreen(navController = navController,)
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