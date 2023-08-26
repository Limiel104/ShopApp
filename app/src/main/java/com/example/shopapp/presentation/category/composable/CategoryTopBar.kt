package com.example.shopapp.presentation.category.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.presentation.common.composable.IconButtonCard
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.CATEGORY_TOP_BAR
import com.example.shopapp.util.Constants.SORT_AND_FILTER_BTN

@Composable
fun CategoryTopBar(
    categoryName: String,
    onSortAndFilterSelected: () -> Unit,
    onCartSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(vertical = 15.dp)
            .testTag(CATEGORY_TOP_BAR),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = categoryName,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            horizontalArrangement = Arrangement.End
        ) {
            IconButtonCard(
                icon = Icons.Outlined.Sort,
                description = SORT_AND_FILTER_BTN,
                outsidePaddingValue = 0,
                onClick = { onSortAndFilterSelected() }
            )

            IconButtonCard(
                icon = Icons.Outlined.ShoppingCart,
                description = CART_BTN,
                outsidePaddingValue = 0,
                onClick = { onCartSelected() }
            )
        }
    }
}

@Preview
@Composable
fun CategoryTopBarPreview() {
    ShopAppTheme {
        CategoryTopBar(
            categoryName = "men's clothing",
            onSortAndFilterSelected = {},
            onCartSelected = {}
        )
    }
}