package com.example.shopapp.presentation.favourites.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.domain.model.Product
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.FAVOURITES_CONTENT
import com.example.shopapp.util.Constants.FAVOURITES_CPI
import com.example.shopapp.util.Constants.FAVOURITES_LAZY_VERTICAL_GRID
import com.example.shopapp.util.Constants.productDescription

@Composable
fun FavouriteContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    productList: List<Product>,
    isLoading: Boolean,
    onProductSelected: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Scaffold(
        topBar = { FavouriteTopBar() },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
            .padding(bottom = bottomBarHeight)
            .testTag(FAVOURITES_CONTENT)
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
                verticalArrangement = Arrangement.spacedBy(25.dp),
                modifier = Modifier
                    .testTag(FAVOURITES_LAZY_VERTICAL_GRID)
            ) {
                itemsIndexed(productList) { _, product ->
                    FavouriteProductItem(
                        product = product,
                        onDelete = { onDelete(product.id) },
                        onClick = { onProductSelected(product.id) }
                    )
                }
            }
        }

        if(productList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(FAVOURITES_CPI),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_favourites)
                )
            }
        }
    }

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag(FAVOURITES_CPI),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun FavouriteContentPreview() {
    ShopAppTheme {
        val productList = listOf(
            Product(
                id = 1,
                title = "Shirt",
                price = "195,59 PLN",
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 2,
                title = "Shirt",
                price = "195,59 PLN",
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
            Product(
                id = 3,
                title = "Shirt",
                price = "195,59 PLN",
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            ),
        )

        FavouriteContent(
            scaffoldState = rememberScaffoldState(),
            bottomBarHeight = 56.dp,
            productList = productList,
            isLoading = false,
            onProductSelected = {},
            onDelete = {}
        )
    }
}

@Preview
@Composable
fun FavouriteContentEmptyListPreview() {
    FavouriteContent(
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = 56.dp,
        productList = emptyList(),
        isLoading = false,
        onProductSelected = {},
        onDelete = {}
    )
}