package com.example.shopapp.presentation.category.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.category.CategoryViewModel

@Composable
fun CategoryScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val categoryId = viewModel.categoryState.value.categoryId
    val productList = viewModel.categoryState.value.productList
    val isSortSectionToggled = viewModel.categoryState.value.isSortSectionToggled

    CategoryContent(
        scaffoldState = scaffoldState,
        categoryName = categoryId,
        productList = productList,
        isSortSectionToggled = isSortSectionToggled
    )
}