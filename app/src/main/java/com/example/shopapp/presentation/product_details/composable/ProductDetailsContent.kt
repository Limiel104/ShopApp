package com.example.shopapp.presentation.product_details.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.productDescription
import com.example.shopapp.util.Constants.productImageUrl
import com.example.shopapp.util.Constants.productName
import com.example.shopapp.util.Constants.productPrice

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetailsContent(
    name: String,
    scaffoldState: BottomSheetScaffoldState,
    onNavigateBack: () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            ProductDetailsBottomSheet(
                name = name,
                price = productPrice,
                description =  productDescription,
                isProductInFavourites = true
            ) },
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetPeekHeight = 100.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            ProductDetailsImageItem(
                imageUrl = productImageUrl,
                onNavigateBack = { onNavigateBack() }
            )
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

        ProductDetailsContent(
            name = productName,
            scaffoldState = scaffoldState,
            onNavigateBack = {}
        )
    }
}