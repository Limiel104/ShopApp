package com.example.shopapp.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopapp.R
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.IMAGE
import com.example.shopapp.util.Constants.productItemImageHeight
import com.example.shopapp.util.Constants.productItemImageWidth

@Composable
fun ImageItem(
    imageUrl: String,
    width: Int,
    height: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(width.dp, height.dp)
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .placeholder(R.drawable.ic_no_image)
                .build(),
            contentDescription = IMAGE,
            fallback = painterResource(R.drawable.ic_no_image),
            error = painterResource(R.drawable.ic_no_image),
            contentScale = ContentScale.Fit
        )
    }
}

@Preview
@Composable
fun ImageItemPreview() {
    Surface() {
        ShopAppTheme() {
            ImageItem(
                imageUrl = "",
                width = productItemImageWidth,
                height = productItemImageHeight,
                onClick = {}
            )
        }
    }
}