package com.example.shopapp.presentation.favourites.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable

@Composable
fun FavouritesScreen(
    onNavigateToProductDetails: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    FavouritesContent(
        scaffoldState = scaffoldState,
        onNavigateToProductDetails = onNavigateToProductDetails
    )
}