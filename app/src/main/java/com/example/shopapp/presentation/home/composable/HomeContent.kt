package com.example.shopapp.presentation.home.composable

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun HomeContent(
    scaffoldState: ScaffoldState,
    offerList: List<String>,
    onNavigateToCategory: () -> Unit
) {
    Scaffold(
        topBar = { HomeTopBar() },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                itemsIndexed(offerList) { _, offer ->
                    OfferItem(
                        text = offer,
                        onClick = { onNavigateToCategory() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeContentPreview() {
    ShopAppTheme {
        val offerList = listOf(
            "All clothes for women now 10% cheaper",
            "All clothes for men now 15% cheaper",
            "All shirts 20% cheaper with code SHIRT20",
            "Buy two pairs of pants for the price of one",
            "13% off for purchase above 200$"
        )

        HomeContent(
            scaffoldState = rememberScaffoldState(),
            offerList = offerList,
            onNavigateToCategory = {}
        )
    }
}