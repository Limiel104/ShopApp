package com.example.shopapp.presentation.common

import androidx.navigation.NavController
import com.example.shopapp.util.Screen

fun getLastDestination(navController: NavController): String {
    val destinationQueue = mutableListOf<String>()

    for(destination in navController.backQueue) {
        val dest = destination.destination.route
        if((dest != Screen.SignupScreen.route) && (dest != Screen.LoginScreen.route)) {
            dest?.let { destinationQueue.add(it) }
        }
    }

    return destinationQueue.last()
}