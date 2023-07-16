package com.example.shopapp.presentation.favourite.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun FavouriteContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    productList: List<String>,
    onProductSelected: (String) -> Unit
) {
    Scaffold(
        topBar = { FavouriteTopBar() },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
            .padding(bottom = bottomBarHeight)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 10.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                itemsIndexed(productList) { _, product ->
                    FavouriteProductItem(
                        title = product,
                        onClick = { onProductSelected(product) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun FavouriteContentPreview() {
    ShopAppTheme {
        val productList = listOf(
            "Shirt with regular line",
            "Shorts with flowers",
            "Jumper with regular line",
            "Skirt in black",
            "Shirt with irregular line"
        )

        FavouriteContent(
            scaffoldState = rememberScaffoldState(),
            bottomBarHeight = 56.dp,
            productList = productList,
            onProductSelected = {}
        )
    }
}