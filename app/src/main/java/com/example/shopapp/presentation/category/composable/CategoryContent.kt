package com.example.shopapp.presentation.category.composable

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.Product
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CATEGORY_CPI
import com.example.shopapp.util.Constants.productDescription

@Composable
fun CategoryContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    categoryName: String,
    productList: List<Product>,
    isSortSectionVisible: Boolean,
    isLoading: Boolean,
    isButtonLocked: Boolean,
    isDialogActivated: Boolean,
    sliderPosition: ClosedFloatingPointRange<Float>,
    sliderRange: ClosedFloatingPointRange<Float>,
    onProductSelected: (Int) -> Unit,
    onSortSelected: () -> Unit,
    onFavourite: (Int) -> Unit,
    onDismiss: () -> Unit,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit
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
                Column() {
                    SortSection()
                    FilterSection(
                        sliderPosition = sliderPosition,
                        sliderRange = sliderRange,
                        onValueChange = { onValueChange(it) }
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                itemsIndexed(productList) { _, product ->
                    ProductItem(
                        product = product,
                        isButtonLocked = isButtonLocked,
                        onImageClick = { onProductSelected(product.id) },
                        onFavourite = { onFavourite(product.id) }
                    )
                }
            }
        }

        if(isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(CATEGORY_CPI),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if(isDialogActivated) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                NotLoggedInDialog(
                    onDismiss = { onDismiss() }
                )
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
                price = "195,59 PLN",
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
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
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Shirt",
                price = "195,59 PLN",
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            )
        )

        CategoryContent(
            scaffoldState = rememberScaffoldState(),
            bottomBarHeight = 56.dp,
            categoryName = "men's clothing",
            productList = productList,
            isSortSectionVisible = false,
            isLoading = false,
            isButtonLocked = false,
            isDialogActivated = false,
            sliderPosition = 1f..4f,
            sliderRange = 0f..5f,
            onProductSelected = {},
            onSortSelected = {},
            onFavourite = {},
            onDismiss = {},
            onValueChange = {}
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
                isInFavourites = false
            ),
            Product(
                id = 3,
                title = "Shirt",
                price = "195,59 PLN",
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = false
            ),
            Product(
                id = 4,
                title = "Shirt",
                price = "195,59 PLN",
                description = productDescription,
                category = "men's clothing",
                imageUrl = "imageUrl",
                isInFavourites = true
            )
        )

        CategoryContent(
            scaffoldState = rememberScaffoldState(),
            bottomBarHeight = 56.dp,
            categoryName = "men's clothing",
            productList = productList,
            isSortSectionVisible = true,
            isLoading = false,
            isButtonLocked = false,
            isDialogActivated = false,
            sliderPosition = 1f..4f,
            sliderRange = 0f..5f,
            onProductSelected = {},
            onSortSelected = {},
            onFavourite = {},
            onDismiss = {},
            onValueChange = {}
        )
    }
}