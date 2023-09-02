package com.example.shopapp.presentation.cart.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.R
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.presentation.home.composable.CartItem
import com.example.shopapp.util.Constants.CART_CONTENT
import com.example.shopapp.util.Constants.CART_CPI
import com.example.shopapp.util.Constants.CART_LAZY_COLUMN
import com.example.shopapp.util.Constants.ORDER_BTN
import com.example.shopapp.util.Constants.CART_TOTAL_AMOUNT_ROW

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CartContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    cartProducts: List<CartProduct>,
    totalAmount: Double,
    isLoading: Boolean,
    isDialogActivated: Boolean,
    onGoBack: () -> Unit,
    onGoHome: () -> Unit
) {
    Scaffold(
        topBar = {
            CartTopBar(
                onClick = { onGoBack() }
            )
        },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
            .padding(bottom = bottomBarHeight)
            .testTag(CART_CONTENT)
    ) {
        if (cartProducts.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 10.dp)
                        .testTag(CART_LAZY_COLUMN)
                ) {
                    itemsIndexed(cartProducts) { _, item ->
                        CartItem(
                            cartProduct = item,
                            onImageClick = {},
                            onPlus = {},
                            onMinus = {}
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .testTag(CART_TOTAL_AMOUNT_ROW),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.total_amount),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )

                    Text(
                        text = String.format("%.2f PLN", totalAmount),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }

                ShopButtonItem(
                    text = stringResource(id = R.string.order),
                    testTag = ORDER_BTN,
                    onClick = { }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        else {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.empty_cart)
                )
            }
        }
    }

    if(isDialogActivated) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            OrderPlacedDialog(
                onGoHome = { onGoHome() }
            )
        }
    }

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag(CART_CPI),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

private fun returnCartProducts(): List<CartProduct> {
    return listOf(
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
}

@Preview
@Composable
fun CartContentPreview() {
    CartContent(
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = 56.dp,
        totalAmount = 155.45,
        cartProducts = returnCartProducts(),
        isLoading = false,
        isDialogActivated = false,
        onGoBack = {},
        onGoHome = {}
    )
}

@Preview
@Composable
fun CartContentPreviewListIsEmpty() {
    CartContent(
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = 56.dp,
        cartProducts = emptyList(),
        totalAmount = 155.45,
        isLoading = false,
        isDialogActivated = false,
        onGoBack = {},
        onGoHome = {}
    )
}

@Preview
@Composable
fun CartContentPreviewDialogActivated() {
    CartContent(
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = 56.dp,
        cartProducts = returnCartProducts(),
        totalAmount = 155.45,
        isLoading = false,
        isDialogActivated = true,
        onGoBack = {},
        onGoHome = {}
    )
}

@Preview
@Composable
fun CartContentPreviewLoading() {
    CartContent(
        scaffoldState = rememberScaffoldState(),
        bottomBarHeight = 56.dp,
        cartProducts = returnCartProducts(),
        totalAmount = 155.45,
        isLoading = true,
        isDialogActivated = false,
        onGoBack = {},
        onGoHome = {}
    )
}