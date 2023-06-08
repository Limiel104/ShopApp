package com.example.shopapp.presentation.category_list.composable

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryListScreen(
    onNavigateToCategory: () -> Unit
) {
    CategoryListContent(
        onNavigateToCategory = onNavigateToCategory
    )
}