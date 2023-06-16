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
import com.example.shopapp.domain.model.Offer
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun HomeContent(
    scaffoldState: ScaffoldState,
    offerList: List<Offer>,
    onOfferSelected: (String) -> Unit
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
                        text = offer.description,
                        onClick = { onOfferSelected(offer.categoryId) }
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
            Offer(
                categoryId = "women's clothing",
                discountPercent = 10,
                description = "All clothes for women now 10% cheaper"
            ),
            Offer(
                categoryId = "men's clothing",
                discountPercent = 15,
                description = "All clothes for men now 15% cheaper"
            ),
            Offer(
                categoryId = "jewelery",
                discountPercent = 50,
                description = "Buy two pieces of jewelery for the price of one"
            ),
            Offer(
                categoryId = "",
                discountPercent = 13,
                description = "13% off for purchase above 200\$"
            )
        )

        HomeContent(
            scaffoldState = rememberScaffoldState(),
            offerList = offerList,
            onOfferSelected = {}
        )
    }
}