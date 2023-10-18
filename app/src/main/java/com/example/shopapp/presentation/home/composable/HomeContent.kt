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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.Offer
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.HOME_CONTENT
import com.example.shopapp.util.Constants.HOME_LAZY_COLUMN

@Composable
fun HomeContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    offerList: List<Offer>,
    onOfferSelected: (String) -> Unit,
    onGoToCart: () -> Unit
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                onClick = { onGoToCart() }
            ) },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .testTag(HOME_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(HOME_LAZY_COLUMN),
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
            bottomBarHeight = 56.dp,
            offerList = offerList,
            onOfferSelected = {},
            onGoToCart = {}
        )
    }
}