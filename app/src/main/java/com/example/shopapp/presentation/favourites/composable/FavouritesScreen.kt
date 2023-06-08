package com.example.shopapp.presentation.favourites.composable

import androidx.compose.runtime.Composable

@Composable
fun FavouritesScreen(
    onNavigateToProductDetails: () -> Unit
) {
    FavouritesContent(
        onNavigateToProductDetails = onNavigateToProductDetails
    )
}