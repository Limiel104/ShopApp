package com.example.shopapp.presentation.category_list.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.presentation.common.composable.CardIconButton
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun CategoryListTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(10.dp, 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        CardIconButton(
            icon = Icons.Outlined.ShoppingCart,
            description = "Cart",
            outsidePaddingValue = 0,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun CategoryListTopBarPreview() {
    ShopAppTheme {
        CategoryListTopBar()
    }
}