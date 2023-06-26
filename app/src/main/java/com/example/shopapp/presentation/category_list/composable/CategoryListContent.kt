package com.example.shopapp.presentation.category_list.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CATEGORY_LIST_LAZY_COLUMN

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryListContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf(
        "jewelery",
        "men's clothing",
        "women's clothing",
        "all"
    )
    Scaffold(
        topBar = { CategoryListTopBar() },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = bottomBarHeight)
    ) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(CATEGORY_LIST_LAZY_COLUMN),
            ) {
                itemsIndexed(categories) { _, category ->
                    CategoryListItem(
                        name = category,
                        onClick = { onCategorySelected(category) }
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
            scaffoldState = rememberScaffoldState(),
            bottomBarHeight = 56.dp,
            onCategorySelected = {}
        )
    }
}