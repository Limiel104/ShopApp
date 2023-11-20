package com.example.shopapp.presentation.cart.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.domain.model.CartProduct
import com.example.shopapp.presentation.common.Constants.CART_CONTENT
import com.example.shopapp.presentation.common.Constants.CART_CPI
import com.example.shopapp.presentation.common.Constants.CART_LAZY_COLUMN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    snackbarHostState: SnackbarHostState,
    cartProducts: List<CartProduct>,
    totalAmount: Double,
    isLoading: Boolean,
    isDialogActivated: Boolean,
    onPlus: (Int) -> Unit,
    onMinus: (Int) -> Unit,
    onGoBack: () -> Unit,
    onDelete: (Int) -> Unit,
    onOrderPlaced: () -> Unit,
    onGoHome: () -> Unit,
) {
    Scaffold(
        topBar = {
            CartTopBar(
                onClick = { onGoBack() }
            ) },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) },
        modifier = Modifier
            .fillMaxSize()
            .testTag(CART_CONTENT)
    ) { paddingValues ->
        if (cartProducts.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .testTag(CART_LAZY_COLUMN)
                ) {
                    itemsIndexed(
                        items = cartProducts,
                        key = { _, item -> item.hashCode() }
                    ) { index, cartProduct ->
                        val dismissState = rememberDismissState(
                            confirmValueChange = {
                                if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                    onDelete(cartProduct.id)
                                    true
                                } else false
                            }
                        )

                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                            background = {
                                val color = when(dismissState.dismissDirection) {
                                    DismissDirection.StartToEnd -> MaterialTheme.colorScheme.secondary
                                    DismissDirection.EndToStart -> MaterialTheme.colorScheme.secondary
                                    null -> Color.Transparent
                                }

                                val alignment = when(dismissState.dismissDirection) {
                                    DismissDirection.StartToEnd -> Alignment.CenterStart
                                    DismissDirection.EndToStart -> Alignment.CenterEnd
                                    null -> Alignment.Center
                                }

                                val tint = when(dismissState.dismissDirection) {
                                    DismissDirection.StartToEnd -> MaterialTheme.colorScheme.onSecondary
                                    DismissDirection.EndToStart -> MaterialTheme.colorScheme.onSecondary
                                    null -> Color.Transparent
                                }

                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(color)
                                        .padding(vertical = 30.dp),
                                    contentAlignment = alignment
                                ){
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = stringResource(id = R.string.delete_cart_product),
                                        tint = tint
                                    )
                                }
                            },
                            dismissContent = {
                                CartProductItem(
                                    cartProduct = cartProduct,
                                    onImageClick = {},
                                    onPlus = { onPlus(it) },
                                    onMinus = { onMinus(it) }
                                )
                            }
                        )

                        if(index != cartProducts.size-1){
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }

                TotalAmountSection(
                    totalAmount = totalAmount,
                    onOrderPlaced = { onOrderPlaced() }
                )
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
            price = 123.45,
            imageUrl = "",
            amount = 1
        ),
        CartProduct(
            id = 2,
            title = "title 2",
            price = 53.34,
            imageUrl = "",
            amount = 2
        ),
        CartProduct(
            id = 3,
            title = "title 3",
            price = 56.00,
            imageUrl = "",
            amount = 1
        ),
        CartProduct(
            id = 4,
            title = "title 4",
            price = 23.00,
            imageUrl = "",
            amount = 1
        ),
        CartProduct(
            id = 5,
            title = "title 5",
            price = 6.86,
            imageUrl = "",
            amount = 2
        ),
        CartProduct(
            id = 6,
            title = "title 6 dsfhdkjhgdfjkg hdfjkghdfkghfjdh fdhkghdkfjghdfkjghdfjghdfkjghdfkjghdfk",
            price = 44.99,
            imageUrl = "",
            amount = 3
        ),
        CartProduct(
            id = 7,
            title = "title 7",
            price = 203.99,
            imageUrl = "",
            amount = 3
        )
    )
}

@Preview
@Composable
fun CartContentPreview() {
    val snackbarHostState = remember { SnackbarHostState() }

    CartContent(
        snackbarHostState = snackbarHostState,
        totalAmount = 155.45,
        cartProducts = returnCartProducts(),
        isLoading = false,
        isDialogActivated = false,
        onPlus = {},
        onMinus = {},
        onGoBack = {},
        onDelete = {},
        onOrderPlaced = {},
        onGoHome = {}
    )
}

@Preview
@Composable
fun CartContentPreviewListIsEmpty() {
    val snackbarHostState = remember { SnackbarHostState() }

    CartContent(
        snackbarHostState = snackbarHostState,
        cartProducts = emptyList(),
        totalAmount = 155.45,
        isLoading = false,
        isDialogActivated = false,
        onPlus = {},
        onMinus = {},
        onGoBack = {},
        onDelete = {},
        onOrderPlaced = {},
        onGoHome = {}
    )
}

@Preview
@Composable
fun CartContentPreviewDialogActivated() {
    val snackbarHostState = remember { SnackbarHostState() }

    CartContent(
        snackbarHostState = snackbarHostState,
        cartProducts = returnCartProducts(),
        totalAmount = 155.45,
        isLoading = false,
        isDialogActivated = true,
        onPlus = {},
        onMinus = {},
        onGoBack = {},
        onDelete = {},
        onOrderPlaced = {},
        onGoHome = {}
    )
}

@Preview
@Composable
fun CartContentPreviewLoading() {
    val snackbarHostState = remember { SnackbarHostState() }

    CartContent(
        snackbarHostState = snackbarHostState,
        cartProducts = returnCartProducts(),
        totalAmount = 155.45,
        isLoading = true,
        isDialogActivated = false,
        onPlus = {},
        onMinus = {},
        onGoBack = {},
        onDelete = {},
        onOrderPlaced = {},
        onGoHome = {}
    )
}