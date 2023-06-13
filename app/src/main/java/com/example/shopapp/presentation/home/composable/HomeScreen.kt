package com.example.shopapp.presentation.home.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopapp.presentation.home.HomeViewModel

@Composable
fun HomeScreen(
    onNavigateToCategory: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val offerList = viewModel.homeState.value.offerList

    HomeContent(
        scaffoldState = scaffoldState,
        offerList = offerList,
        onNavigateToCategory = onNavigateToCategory
    )
}
