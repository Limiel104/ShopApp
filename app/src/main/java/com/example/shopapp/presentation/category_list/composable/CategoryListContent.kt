package com.example.shopapp.presentation.category_list.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants.CATEGORY_LIST_CONTENT
import com.example.shopapp.util.Constants.CATEGORY_LIST_LAZY_COLUMN
import com.example.shopapp.util.getCategory

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryListContent(
    categoryList: List<Category>,
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
                itemsIndexed(categoryList) { _, category ->
                    CategoryListItem(
                        name = category.title,
                        onClick = { onCategorySelected(category.id) }
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
            categoryList = getCategory(),
            onCategorySelected = {},
            onGoToCart = {}
        )
    }
}