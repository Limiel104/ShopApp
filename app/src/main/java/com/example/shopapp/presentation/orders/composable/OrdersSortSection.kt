package com.example.shopapp.presentation.orders.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.domain.util.OrderOrder
import com.example.shopapp.presentation.category.composable.SortSectionItem
import com.example.shopapp.util.Constants.ORDERS_SORT_SECTION

@Composable
fun OrdersSortSection(
    orderOrder: OrderOrder,
    onOrderChange: (OrderOrder) -> Unit
) {
    Column {
        Divider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(vertical = 10.dp)
                .testTag(ORDERS_SORT_SECTION),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SortSectionItem(
                text = stringResource(id = R.string.date_ascending),
                selected = orderOrder is OrderOrder.DateAscending,
                onClick = { onOrderChange(OrderOrder.DateAscending()) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            SortSectionItem(
                text = stringResource(id = R.string.date_descending),
                selected = orderOrder is OrderOrder.DateDescending,
                onClick = { onOrderChange(OrderOrder.DateDescending()) }
            )
        }

        Divider()
    }
}

@Preview
@Composable
fun OrdersSortSectionPreview() {
    OrdersSortSection(
        orderOrder = OrderOrder.DateDescending(),
        onOrderChange = {}
    )
}