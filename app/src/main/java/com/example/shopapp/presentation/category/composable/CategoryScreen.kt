package com.example.shopapp.presentation.category.composable

import androidx.compose.runtime.Composable

@Composable
fun CategoryScreen(
    onNavigateToProductDetails: () -> Unit
) {
    CategoryContent(
        onNavigateToProductDetails = onNavigateToProductDetails
    )
}