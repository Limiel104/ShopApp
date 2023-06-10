package com.example.shopapp.presentation.home.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onNavigateToCategory: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    HomeContent(
        scaffoldState = scaffoldState,
        onNavigateToCategory = onNavigateToCategory
    )
}
