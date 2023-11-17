package com.example.shopapp.presentation.category_list.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CATEGORY_LIST_CONTENT
import com.example.shopapp.util.Constants.CATEGORY_LIST_LAZY_COLUMN

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryListContent(
    categoryList: List<String>,
    onCategorySelected: (String) -> Unit,
    onGoToCart: () -> Unit
) {
    Scaffold(
        topBar = {
            CategoryListTopBar(
                onClick = { onGoToCart() }
            ) },
        modifier = Modifier
            .fillMaxSize()
            .testTag(CATEGORY_LIST_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(CATEGORY_LIST_LAZY_COLUMN),
            ) {
                items(categoryList) {category ->
                    CategoryListItem(
                        name = category,
                        onClick = { onCategorySelected(category.lowercase()) }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun CategoryListContentPreview() {
    ShopAppTheme {
        CategoryListContent(
            categoryList = listOf(
                "All",
                "Men's clothing",
                "Women's clothing",
                "Jewelery",
                "Electronics"
            ),
            onCategorySelected = {},
            onGoToCart = {}
        )
    }
}