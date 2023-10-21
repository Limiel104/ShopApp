package com.example.shopapp.presentation.category_list.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.category_list.CategoryListEvent
import com.example.shopapp.presentation.category_list.CategoryListUiEvent
import com.example.shopapp.presentation.category_list.CategoryListViewModel
import com.example.shopapp.util.Constants.CATEGORY_LIST_SCREEN_LE
import com.example.shopapp.util.Constants.TAG
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryListScreen(
    navController: NavController,
    viewModel: CategoryListViewModel = hiltViewModel()
) {
    val categoryList = viewModel.categoryListState.value.categoryList

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is CategoryListUiEvent.NavigateToCategory -> {
                    Log.i(TAG, CATEGORY_LIST_SCREEN_LE)
                    navController.navigate(Screen.CategoryScreen.route + "categoryId="+ event.categoryId)
                }
                is CategoryListUiEvent.NavigateToCart -> {
                    navController.navigate(Screen.CartScreen.route)
                }
            }
        }
    }

    CategoryListContent(
        categoryList = categoryList,
        onCategorySelected = { viewModel.onEvent(CategoryListEvent.OnCategorySelected(it)) },
        onGoToCart = { viewModel.onEvent(CategoryListEvent.GoToCart) }
    )
}