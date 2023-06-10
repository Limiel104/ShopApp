package com.example.shopapp.presentation.category.composable

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun CategoryContent(
    scaffoldState: ScaffoldState,
    isSortSectionToggled: Boolean,
    onNavigateToProductDetails: () -> Unit
) {
    Scaffold(
        topBar = {
            CategoryTopBar(
                categoryName = "Category Name",
                onSelected = {}
            ) },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 10.dp)
        ) {

            AnimatedVisibility(
                visible = isSortSectionToggled,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                SortSection()
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                items(20) { i ->
                    ProductItem(
                        isProductInFavourites = i%3 == 0,
                        onClick = { onNavigateToProductDetails() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryContentPreview() {
    ShopAppTheme {
        CategoryContent(
            scaffoldState = rememberScaffoldState(),
            isSortSectionToggled = false,
            onNavigateToProductDetails = {}
        )
    }
}

@Preview
@Composable
fun CategoryContentToggleTruePreview() {
    ShopAppTheme {
        CategoryContent(
            scaffoldState = rememberScaffoldState(),
            isSortSectionToggled = true,
            onNavigateToProductDetails = {}
        )
    }
}