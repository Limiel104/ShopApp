package com.example.shopapp.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductDetailsScreen() {
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = { ProductDetailsBottomSheet(
            name = "DANVOUY Womens T Shirt Casual Cotton Short",
            price = "129,99 PLN",
            description = "95%Cotton,5%Spandex, Features: Casual, Short Sleeve, Letter Print,V-Neck,Fashion Tees, The fabric is soft and has some stretch., Occasion: Casual/Office/Beach/School/Home/Street. Season: Spring,Summer,Autumn,Winter.",
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
                imageUrl = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"
            )
        }
    }
}

@Preview
@Composable
fun ProductDetailsScreenPreview() {
    ShopAppTheme() {
        ProductDetailsScreen()
    }
}