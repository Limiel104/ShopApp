package com.example.shopapp.presentation.product_details.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopapp.R
import com.example.shopapp.domain.model.Product
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.IMAGE
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_COLUMN
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_CONTENT
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_CPI
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_IMAGE_BOX
import com.example.shopapp.util.Constants.productDescription

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsContent(
    scaffoldState: BottomSheetScaffoldState,
    product: Product,
    isLoading: Boolean,
    onFavourite: () -> Unit,
    onAddToCart: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToCart: () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ProductDetailsTopBar(
                onNavigateBack = { onNavigateBack() },
                onNavigateToCart = { onNavigateToCart() }
            ) },
        sheetContent = {
            ProductDetailsBottomSheet(
                product = product,
                onFavourite = { onFavourite() },
                onAddToCart = { onAddToCart() }
            ) },
        sheetContainerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.testTag(PRODUCT_DETAILS_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag(PRODUCT_DETAILS_COLUMN)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
                    .background(Color.White)
                    .testTag(PRODUCT_DETAILS_IMAGE_BOX),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(product.imageUrl)
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
    }

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag(PRODUCT_DETAILS_CPI),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ProductDetailsContentPreview() {
    ShopAppTheme() {
        val scaffoldState = rememberBottomSheetScaffoldState()

        val product = Product(
            id = 1,
            title = "Shirt",
            price = 195.59,
            description = productDescription,
            category = "men's clothing",
            imageUrl = "",
            isInFavourites = true
        )

        ProductDetailsContent(
            scaffoldState = scaffoldState,
            product = product,
            isLoading = false,
            onFavourite = {},
            onAddToCart = {},
            onNavigateBack = {},
            onNavigateToCart = {}
        )
    }
}