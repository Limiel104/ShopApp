package com.example.shopapp.presentation.product_details.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.domain.model.Product
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.categoryName
import com.example.shopapp.util.Constants.emptyString
import com.example.shopapp.util.Constants.productDescription
import com.example.shopapp.util.Constants.productTitle
import com.example.shopapp.util.Constants.productPrice

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetailsContent(
    scaffoldState: BottomSheetScaffoldState,
    product: Product,
    onNavigateBack: () -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            ProductDetailsBottomSheet(
                product = product,
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
                imageUrl = product.imageUrl,
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

        val product = Product(
            id = 1,
            title = productTitle,
            price = productPrice,
            description = productDescription,
            category = categoryName,
            imageUrl = emptyString
        )

        ProductDetailsContent(
            product = product,
            scaffoldState = scaffoldState,
            onNavigateBack = {}
        )
    }
}