package com.example.shopapp.presentation.category_list.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.ui.theme.ShopAppTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CategoryListContent(
    onNavigateToCategory: () -> Unit
) {
    val categories = listOf(
        "jewelery",
        "men's clothing",
        "women's clothing"
    )
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = { CategoryListTopBar() },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) { _ ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                itemsIndexed(categories) { _, category ->
                    CategoryListItem(
                        name = category,
                        onClick = { onNavigateToCategory() }
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
            onNavigateToCategory = {}
        )
    }
}