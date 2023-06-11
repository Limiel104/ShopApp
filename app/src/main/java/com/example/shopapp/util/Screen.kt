package com.example.shopapp.util

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object FavouriteScreen: Screen("favourite_screen")
    object CategoryScreen: Screen("category_screen")
    object CategoryListScreen: Screen("category_list_screen")
    object AccountScreen: Screen("account_screen")
    object ProductDetailsScreen: Screen("product_details_screen")
    object LoginScreen: Screen("login_screen")
    object SignupScreen: Screen("signup_screen")
}