package com.example.shopapp.presentation.product_details.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.Product
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_CONTENT
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_CPI
import com.example.shopapp.util.Constants.bottomSheetPeekHeight
import com.example.shopapp.util.Constants.productDescription

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsContent(
    scaffoldState: BottomSheetScaffoldState,
    product: Product,
    isLoading: Boolean,
    onNavigateBack: () -> Unit,
    onAddToCart: () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            ProductDetailsBottomSheet(
                product = product,
                isProductInFavourites = true
            ) },
        sheetContainerColor = MaterialTheme.colorScheme.background,
        sheetPeekHeight = bottomSheetPeekHeight.dp,
        modifier = Modifier.testTag(PRODUCT_DETAILS_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 15.dp)
                .padding(top = 15.dp)
//                .padding(bottom = bottomSheetPeekHeight.dp)
        ) {
            ProductDetailsImageItem(
                imageUrl = product.imageUrl,
                onNavigateBack = { onNavigateBack() },
                onAddToCart = { onAddToCart() }
            )
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
            onNavigateBack = {},
            onAddToCart = {}
        )
    }
}