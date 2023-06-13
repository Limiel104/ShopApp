package com.example.shopapp.presentation.category_list.composable

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.shopapp.presentation.category_list.CategoryListEvent
import com.example.shopapp.presentation.category_list.CategoryListUiEvent
import com.example.shopapp.presentation.category_list.CategoryListViewModel
import com.example.shopapp.util.Screen
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryListScreen(
    navController: NavController,
    viewModel: CategoryListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is CategoryListUiEvent.NavigateToCategory -> {
                    Log.i("TAG","CategoryListScreen Launched Effect")
                    navController.navigate(Screen.CategoryScreen.route + "categoryId="+ event.categoryId)
                }
            }
        }
    }

    CategoryListContent(
        scaffoldState = scaffoldState,
        onCategorySelected = { categoryId: String ->
            viewModel.onEvent(CategoryListEvent.OnCategorySelected(categoryId))
        }
    )
}