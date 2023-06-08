package com.example.shopapp.presentation.common.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
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

@Composable
fun ImageItem(
    imageUrl: String,
    width: Int? = null,
    height: Int? = null
) {
    Card(
        modifier =
            if (width  == null && height == null)
                Modifier.fillMaxWidth()
            else
                Modifier.size(width!!.dp, height!!.dp),
        backgroundColor = Color.LightGray,
        elevation = 0.dp
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .placeholder(R.drawable.ic_no_image)
                .build(),
            contentDescription = "Image",
            fallback = painterResource(R.drawable.ic_no_image),
            error = painterResource(R.drawable.ic_no_image),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Preview
@Composable
fun ImageItemPreview() {
    ShopAppTheme() {
        ImageItem(
            imageUrl = "",
            width = 180,
            height = 200
        )
    }
}

@Preview
@Composable
fun ImageItemNullPreview() {
    ShopAppTheme() {
        ImageItem(
            imageUrl = ""
        )
    }
}