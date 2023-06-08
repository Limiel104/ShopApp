package com.example.shopapp.presentation.home.composable

import androidx.compose.runtime.Composable

@Composable
fun HomeScreen(
    onNavigateToCategory: () -> Unit
) {
    HomeContent(
        onNavigateToCategory = onNavigateToCategory
    )
}
