package com.example.shopapp.presentation.orders.composable

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.R
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.ORDERS_TOP_BAR
import com.example.shopapp.util.Constants.SORT_BTN

@Composable
fun OrdersTopBar(
    onSortSelected: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.your_orders)) },
        actions = {
            IconButton(onClick = { onSortSelected() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Sort,
                    contentDescription = SORT_BTN
                )
            }
        },
        modifier = Modifier.testTag(ORDERS_TOP_BAR)
    )
}

@Preview
@Composable
fun OrdersTopBarPreview() {
    ShopAppTheme {
        OrdersTopBar(
            onSortSelected = {}
        )
    }
}