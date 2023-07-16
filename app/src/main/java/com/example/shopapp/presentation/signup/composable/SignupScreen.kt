package com.example.shopapp.presentation.signup.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController

@Composable
fun SignupScreen(
    navController: NavController,
    bottomBarHeight: Dp,
) {
    SignupContent(
        bottomBarHeight = bottomBarHeight,
        onSignup = { navController.popBackStack() }
    )
}