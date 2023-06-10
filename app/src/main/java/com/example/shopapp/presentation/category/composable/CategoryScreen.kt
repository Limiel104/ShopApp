package com.example.shopapp.presentation.category.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable

@Composable
fun CategoryScreen(
    onNavigateToProductDetails: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    CategoryContent(
        scaffoldState = scaffoldState,
        isSortSectionToggled = false,
        onNavigateToProductDetails = onNavigateToProductDetails
    )
}