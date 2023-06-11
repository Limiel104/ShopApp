package com.example.shopapp.presentation.category_list.composable

import android.annotation.SuppressLint
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryListScreen(
    onNavigateToCategory: () -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    CategoryListContent(
        scaffoldState = scaffoldState,
        onNavigateToCategory = onNavigateToCategory
    )
}