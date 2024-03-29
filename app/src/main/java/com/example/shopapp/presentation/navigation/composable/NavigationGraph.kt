package com.example.shopapp.presentation.navigation.composable

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.shopapp.presentation.account.composable.AccountScreen
import com.example.shopapp.presentation.cart.composable.CartScreen
import com.example.shopapp.presentation.category.composable.CategoryScreen
import com.example.shopapp.presentation.category_list.composable.CategoryListScreen
import com.example.shopapp.presentation.favourites.composable.FavouriteScreen
import com.example.shopapp.presentation.home.composable.HomeScreen
import com.example.shopapp.presentation.login.composable.LoginScreen
import com.example.shopapp.presentation.orders.composable.OrdersScreen
import com.example.shopapp.presentation.product_details.composable.ProductDetailsScreen
import com.example.shopapp.presentation.profile.composable.ProfileScreen
import com.example.shopapp.presentation.signup.composable.SignupScreen
import com.example.shopapp.presentation.navigation.Screen

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
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.FavouriteScreen.route
        ) {
            FavouriteScreen(navController = navController)
        }
        composable(
            route = Screen.CategoryListScreen.route
        ) {
            CategoryListScreen(navController = navController)
        }
        composable(
            route = Screen.CategoryScreen.route + "categoryId={categoryId}",
            arguments = listOf(
                navArgument(
                    name = "categoryId"
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            CategoryScreen(navController = navController)
        }
        composable(
            route = Screen.AccountScreen.route
        ) {
            AccountScreen(navController = navController)
        }
        composable(
            route = Screen.ProductDetailsScreen.route + "productId={productId}",
            arguments = listOf(
                navArgument(
                    name = "productId"
                ) {
                    type = NavType.IntType
                }
            )
        ) {
            ProductDetailsScreen(navController = navController)
        }
        composable(
            route = Screen.LoginScreen.route
        ) {
            LoginScreen(navController = navController)
        }
        composable(
            route = Screen.SignupScreen.route
        ) {
            SignupScreen(navController = navController)
        }
        composable(
            route = Screen.CartScreen.route
        ) {
            CartScreen(navController = navController)
        }
        composable(
            route = Screen.OrdersScreen.route
        ) {
            OrdersScreen(navController = navController)
        }
        composable(
            route = Screen.ProfileScreen.route + "userUID={userUID}",
            arguments = listOf(
                navArgument(
                    name = "userUID"
                ) {
                    type = NavType.StringType
                }
            )
        ) {
            ProfileScreen(navController = navController)
        }
    }
}