package com.example.shopapp.presentation.orders.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.R
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.presentation.common.Constants.GO_BACK_BTN
import com.example.shopapp.presentation.common.Constants.ORDERS_TOP_BAR
import com.example.shopapp.presentation.common.Constants.SORT_BTN

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersTopBar(
    onSortSelected: () -> Unit,
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.your_orders)) },
        navigationIcon = {
            IconButton(
                onClick = { onNavigateBack() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = GO_BACK_BTN
                )
            }
        },
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
            onSortSelected = {},
            onNavigateBack = {}
        )
    }
}