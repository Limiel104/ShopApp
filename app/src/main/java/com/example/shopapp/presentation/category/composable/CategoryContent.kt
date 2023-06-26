package com.example.shopapp.presentation.category.composable

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.shopapp.domain.model.Product
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.categoryName
import com.example.shopapp.util.Constants.productImageUrl

@Composable
fun CategoryContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    categoryName: String,
    productList: List<Product>,
    isSortSectionVisible: Boolean,
    onProductSelected: (String) -> Unit,
    onSortSelected: () -> Unit
) {
    Scaffold(
        topBar = {
            CategoryTopBar(
                categoryName = categoryName,
                onSortSelected = { onSortSelected() },
                onCartSelected = {}
            ) },
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

            AnimatedVisibility(
                visible = isSortSectionVisible,
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
                itemsIndexed(productList) { index, product ->
                    ProductItem(
                        product = product,
                        isProductInFavourites = index%3 == 0,
                        onClick = { onProductSelected(product.title) }
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
        val productList = listOf(
            Product(
                id = 1,
                title = "Shirt",
                price = 179.99,
                description = "description",
                category = categoryName,
                imageUrl = productImageUrl
            ),
            Product(
                id = 2,
                title = "Shirt",
                price = 179.99,
                description = "description",
                category = categoryName,
                imageUrl = productImageUrl
            ),
            Product(
                id = 3,
                title = "Shirt",
                price = 179.99,
                description = "description",
                category = categoryName,
                imageUrl = productImageUrl
            ),
            Product(
                id = 4,
                title = "Shirt",
                price = 179.99,
                description = "description",
                category = categoryName,
                imageUrl = productImageUrl
            )
        )

        CategoryContent(
            scaffoldState = rememberScaffoldState(),
            bottomBarHeight = 56.dp,
            categoryName = categoryName,
            productList = productList,
            isSortSectionVisible = false,
            onProductSelected = {},
            onSortSelected = {}
        )
    }
}

@Preview
@Composable
fun CategoryContentToggleTruePreview() {
    ShopAppTheme {
        val productList = listOf(
            Product(
                id = 1,
                title = "Shirt",
                price = 179.99,
                description = "description",
                category = categoryName,
                imageUrl = productImageUrl
            ),
            Product(
                id = 2,
                title = "Shirt",
                price = 179.99,
                description = "description",
                category = categoryName,
                imageUrl = productImageUrl
            ),
            Product(
                id = 3,
                title = "Shirt",
                price = 179.99,
                description = "description",
                category = categoryName,
                imageUrl = productImageUrl
            ),
            Product(
                id = 4,
                title = "Shirt",
                price = 179.99,
                description = "description",
                category = categoryName,
                imageUrl = productImageUrl
            )
        )

        CategoryContent(
            scaffoldState = rememberScaffoldState(),
            bottomBarHeight = 56.dp,
            categoryName = categoryName,
            productList = productList,
            isSortSectionVisible = true,
            onProductSelected = {},
            onSortSelected = {}
        )
    }
}