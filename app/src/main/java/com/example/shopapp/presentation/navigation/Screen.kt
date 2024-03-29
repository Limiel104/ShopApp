package com.example.shopapp.presentation.navigation

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object FavouriteScreen: Screen("favourite_screen")
    object CategoryScreen: Screen("category_screen")
    object CategoryListScreen: Screen("category_list_screen")
    object AccountScreen: Screen("account_screen")
    object ProductDetailsScreen: Screen("product_details_screen")
    object LoginScreen: Screen("login_screen")
    object SignupScreen: Screen("signup_screen")
    object CartScreen: Screen("cart_screen")
    object OrdersScreen: Screen("orders_screen")
    object ProfileScreen: Screen("profile_screen")
}