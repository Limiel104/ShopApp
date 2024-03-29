package com.example.shopapp.presentation.category.composable

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.Product
import com.example.shopapp.domain.util.ProductOrder
import com.example.shopapp.presentation.common.composable.NotLoggedInDialog
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.presentation.common.Constants.CATEGORY_CONTENT
import com.example.shopapp.presentation.common.Constants.CATEGORY_CPI
import com.example.shopapp.presentation.common.Constants.CATEGORY_LAZY_VERTICAL_GRID
import com.example.shopapp.presentation.common.Constants.productDescription

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryContent(
    categoryName: String,
    productList: List<Product>,
    isSortAndFilterSectionVisible: Boolean,
    isLoading: Boolean,
    isButtonEnabled: Boolean,
    isDialogActivated: Boolean,
    sliderPosition: ClosedFloatingPointRange<Float>,
    sliderRange: ClosedFloatingPointRange<Float>,
    productOrder: ProductOrder,
    categoryFilterMap: Map<String,Boolean>,
    onProductSelected: (Int) -> Unit,
    onSortAndFilterSelected: () -> Unit,
    onFavourite: (Int) -> Unit,
    onDismiss: () -> Unit,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onOrderChange: (ProductOrder) -> Unit,
    onCheckedChange: (String) -> Unit,
    onGoToCart: () -> Unit
) {
    Scaffold(
        topBar = {
            CategoryTopBar(
                categoryName = categoryName,
                onSortAndFilterSelected = { onSortAndFilterSelected() },
                onCartSelected = { onGoToCart() }
            ) },
        modifier = Modifier
            .fillMaxSize()
            .testTag(CATEGORY_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 10.dp)
        ) {
            AnimatedVisibility(
                visible = isSortAndFilterSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                Column() {
                    SortSection(
                        productOrder = productOrder,
                        onOrderChange = { onOrderChange(it) }
                    )
                    FilterSection(
                        sliderPosition = sliderPosition,
                        sliderRange = sliderRange,
                        isCategoryFilterVisible = categoryName == "all",
                        categoryFilterMap = categoryFilterMap,
                        onValueChange = { onValueChange(it) },
                        onCheckedChange = { onCheckedChange(it) }
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(25.dp),
                modifier = Modifier
                    .testTag(CATEGORY_LAZY_VERTICAL_GRID)
            ) {
                itemsIndexed(productList) { _, product ->
                    ProductItem(
                        product = product,
                        isButtonEnabled = isButtonEnabled,
                        onImageClick = { onProductSelected(product.id) },
                        onFavourite = { onFavourite(product.id) }
                    )
                }
            }
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
}

private fun getProductList(): List<Product> {
    return listOf(
        Product(
            id = 1,
            title = "Shirt",
            price = 195.59,
            description = productDescription,
            category = "men's clothing",
            imageUrl = "imageUrl",
            isInFavourites = false
        ),
        Product(
            id = 2,
            title = "Shirt",
            price = 195.59,
            description = productDescription,
            category = "men's clothing",
            imageUrl = "imageUrl",
            isInFavourites = true
        ),
        Product(
            id = 3,
            title = "Shirt",
            price = 195.59,
            description = productDescription,
            category = "men's clothing",
            imageUrl = "imageUrl",
            isInFavourites = false
        ),
        Product(
            id = 4,
            title = "Shirt",
            price = 195.59,
            description = productDescription,
            category = "men's clothing",
            imageUrl = "imageUrl",
            isInFavourites = true
        )
    )
}

@Preview
@Composable
fun CategoryContentPreview() {
    ShopAppTheme {
        CategoryContent(
            categoryName = "men's clothing",
            productList = getProductList(),
            isSortAndFilterSectionVisible = false,
            isLoading = false,
            isButtonEnabled = true,
            isDialogActivated = false,
            sliderPosition = 1f..4f,
            sliderRange = 0f..5f,
            productOrder = ProductOrder.NameAscending(),
            categoryFilterMap = mapOf(),
            onProductSelected = {},
            onSortAndFilterSelected = {},
            onFavourite = {},
            onDismiss = {},
            onValueChange = {},
            onOrderChange = {},
            onCheckedChange = {},
            onGoToCart = {}
        )
    }
}

@Preview
@Composable
fun CategoryContentToggleTruePreviewCategoryIsNotAll() {
    ShopAppTheme {
        CategoryContent(
            categoryName = "men's clothing",
            productList = getProductList(),
            isSortAndFilterSectionVisible = true,
            isLoading = false,
            isButtonEnabled = true,
            isDialogActivated = false,
            sliderPosition = 1f..4f,
            sliderRange = 0f..5f,
            productOrder = ProductOrder.NameDescending(),
            categoryFilterMap = mapOf(),
            onProductSelected = {},
            onSortAndFilterSelected = {},
            onFavourite = {},
            onDismiss = {},
            onValueChange = {},
            onOrderChange = {},
            onCheckedChange = {},
            onGoToCart = {}
        )
    }
}

@Preview
@Composable
fun CategoryContentToggleTruePreviewCategoryIsAll() {
    ShopAppTheme {
        CategoryContent(
            categoryName = "all",
            productList = getProductList(),
            isSortAndFilterSectionVisible = true,
            isLoading = false,
            isButtonEnabled = true,
            isDialogActivated = false,
            sliderPosition = 1f..4f,
            sliderRange = 0f..5f,
            productOrder = ProductOrder.NameDescending(),
            categoryFilterMap = mapOf(
                Pair("Men's clothing",true),
                Pair("Women's clothing",true),
                Pair("Jewelery",true),
                Pair("Electronics",true)
            ),
            onProductSelected = {},
            onSortAndFilterSelected = {},
            onFavourite = {},
            onDismiss = {},
            onValueChange = {},
            onOrderChange = {},
            onCheckedChange = {},
            onGoToCart = {}
        )
    }
}

@Preview
@Composable
fun CategoryContentDialogPreview() {
    ShopAppTheme {
        CategoryContent(
            categoryName = "all",
            productList = getProductList(),
            isSortAndFilterSectionVisible = false,
            isLoading = false,
            isButtonEnabled = true,
            isDialogActivated = true,
            sliderPosition = 1f..4f,
            sliderRange = 0f..5f,
            productOrder = ProductOrder.NameDescending(),
            categoryFilterMap = mapOf(),
            onProductSelected = {},
            onSortAndFilterSelected = {},
            onFavourite = {},
            onDismiss = {},
            onValueChange = {},
            onOrderChange = {},
            onCheckedChange = {},
            onGoToCart = {}
        )
    }
}

@Preview
@Composable
fun CategoryContentCPIPreview() {
    ShopAppTheme {
        CategoryContent(
            categoryName = "all",
            productList = getProductList(),
            isSortAndFilterSectionVisible = false,
            isLoading = true,
            isButtonEnabled = true,
            isDialogActivated = false,
            sliderPosition = 1f..4f,
            sliderRange = 0f..5f,
            productOrder = ProductOrder.NameDescending(),
            categoryFilterMap = mapOf(),
            onProductSelected = {},
            onSortAndFilterSelected = {},
            onFavourite = {},
            onDismiss = {},
            onValueChange = {},
            onOrderChange = {},
            onCheckedChange = {},
            onGoToCart = {}
        )
    }
}