package com.example.shopapp.presentation.home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopapp.R
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.HOME_BANNER
import com.example.shopapp.util.Constants.IMAGE

@Composable
fun BannerItem(
    resourceId: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("$HOME_BANNER $resourceId"),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(resourceId)
                .crossfade(true)
                .placeholder(R.drawable.ic_no_image)
                .build(),
            contentDescription = IMAGE,
            fallback = painterResource(R.drawable.ic_no_image),
            error = painterResource(R.drawable.ic_no_image),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        )
    }
}

@Preview
@Composable
fun BannerItemPreview() {
    Surface() {
        ShopAppTheme {
            BannerItem(
                resourceId = R.drawable.jewelery_banner,
                onClick = {}
            )
        }
    }
}