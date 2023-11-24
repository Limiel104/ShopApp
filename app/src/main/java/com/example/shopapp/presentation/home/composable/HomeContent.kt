package com.example.shopapp.presentation.home.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.domain.model.Banner
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.presentation.common.Constants.HOME_CONTENT
import com.example.shopapp.presentation.common.Constants.HOME_LAZY_COLUMN

@Composable
fun HomeContent(
    bannerList: List<Banner>,
    onOfferSelected: (String) -> Unit,
    onGoToCart: () -> Unit
) {
    Scaffold(
        topBar = {
            HomeTopBar(
                onClick = { onGoToCart() }
            ) },
        modifier = Modifier
            .fillMaxSize()
            .testTag(HOME_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(HOME_LAZY_COLUMN),
            ) {
                itemsIndexed(bannerList) { index, banner ->
                    BannerItem(
                        resourceId = banner.resourceId,
                        onClick = { onOfferSelected(banner.categoryId) }
                    )

                    if(index != bannerList.size-1){
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeContentPreview() {
    ShopAppTheme {
        val bannerLists = listOf(
            Banner(
                categoryId = "women's clothing",
                resourceId = R.drawable.womans_clothing_banner
            ),
            Banner(
                categoryId = "men's clothing",
                resourceId = R.drawable.mens_clothing_banner
            ),
            Banner(
                categoryId = "jewelery",
                resourceId = R.drawable.jewelery_banner
            ),
            Banner(
                categoryId = "electronics",
                resourceId = R.drawable.electronics_banner
            )
        )

        HomeContent(
            bannerList = bannerLists,
            onOfferSelected = {},
            onGoToCart = {}
        )
    }
}