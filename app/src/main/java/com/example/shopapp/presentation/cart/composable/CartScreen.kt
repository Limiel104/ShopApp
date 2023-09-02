package com.example.shopapp.presentation.cart.composable

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.example.shopapp.domain.model.CartProduct

@Composable
fun CartScreen(
    navController: NavController,
    bottomBarHeight: Dp
) {
    val scaffoldState = rememberScaffoldState()
    val cartItems = listOf(
        CartProduct(
            id = 1,
            title = "title 1",
            price = "123,45 PLN",
            imageUrl = "",
            amount = 1
        ),
        CartProduct(
            id = 2,
            title = "title 2",
            price = "53,34 PLN",
            imageUrl = "",
            amount = 2
        ),
        CartProduct(
            id = 3,
            title = "title 3",
            price = "56,00 PLN",
            imageUrl = "",
            amount = 1
        ),
        CartProduct(
            id = 4,
            title = "title 4",
            price = "23,00 PLN",
            imageUrl = "",
            amount = 1
        ),
        CartProduct(
            id = 5,
            title = "title 5",
            price = "6,86 PLN",
            imageUrl = "",
            amount = 2
        ),
        CartProduct(
            id = 6,
            title = "title 6 dsfhdkjhgdfjkg hdfjkghdfkghfjdh fdhkghdkfjghdfkjghdfjghdfkjghdfkjghdfk",
            price = "44,99 PLN",
            imageUrl = "",
            amount = 3
        ),
        CartProduct(
            id = 7,
            title = "title 7",
            price = "203,99 PLN",
            imageUrl = "",
            amount = 3
        )
    )

    CartContent(
        scaffoldState = scaffoldState,
        bottomBarHeight = bottomBarHeight,
        totalAmount = 155.45,
        cartProducts = cartItems,
        isDialogActivated = false,
        onGoBack = {},
        onGoHome = {}
    )
}