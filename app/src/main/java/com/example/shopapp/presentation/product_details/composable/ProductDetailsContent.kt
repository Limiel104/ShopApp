package com.example.shopapp.presentation.product_details.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.Product
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants
import com.example.shopapp.util.Constants.PRODUCT_DETAILS_CONTENT
import com.example.shopapp.util.Constants.bottomSheetPeekHeight
import com.example.shopapp.util.Constants.productDescription

@OptIn(ExperimentalMaterialApi::class)
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
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetPeekHeight = bottomSheetPeekHeight.dp,
        modifier = Modifier.testTag(PRODUCT_DETAILS_CONTENT)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
                .padding(top = 15.dp)
                .padding(bottom = bottomSheetPeekHeight.dp)
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
                .testTag(Constants.FAVOURITES_CPI),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ProductDetailsContentPreview() {
    ShopAppTheme() {
        val bottomSheetState = rememberBottomSheetState(
            initialValue = BottomSheetValue.Collapsed,
        )
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = bottomSheetState
        )

        val product = Product(
            id = 1,
            title = "Shirt",
            price = "195,59 PLN",
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