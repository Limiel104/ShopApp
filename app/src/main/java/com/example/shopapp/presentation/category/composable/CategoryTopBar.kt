package com.example.shopapp.presentation.category.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun CategoryTopBar(
    categoryName: String,
    onSelected: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(vertical = 15.dp),
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
            Icon(
                imageVector = Icons.Outlined.Sort,
                contentDescription = "Sort",
                modifier = Modifier
                    .clickable { onSelected() }
            )

            Spacer(modifier = Modifier.width(15.dp))

            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "Cart",
                modifier = Modifier
                    .clickable {}
            )
        }
    }
}

@Preview
@Composable
fun CategoryTopBarPreview() {
    ShopAppTheme {
        CategoryTopBar(
            categoryName = "Category Name",
            onSelected = {}
        )
    }
}