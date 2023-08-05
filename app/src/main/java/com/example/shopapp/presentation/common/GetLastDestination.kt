package com.example.shopapp.presentation.common

import androidx.navigation.NavController
import com.example.shopapp.util.Screen

fun getLastDestination(navController: NavController): String {
    val destinationQueue = mutableListOf<String>()

    for(destination in navController.backQueue) {
        val route = destination.destination.route
        if((route != Screen.SignupScreen.route) && (route != Screen.LoginScreen.route)) {
            route?.let { destinationQueue.add(it) }
        }
    }

    return destinationQueue.last()
}