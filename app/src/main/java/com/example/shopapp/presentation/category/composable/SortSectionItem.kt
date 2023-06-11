package com.example.shopapp.presentation.category.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun SortSectionItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            modifier = Modifier
                .size(25.dp)
                .testTag(text),
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.secondary,
                unselectedColor = MaterialTheme.colors.onSecondary
            ),
        )

        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(start = 3.dp)
        )
    }
}

@Preview
@Composable
fun SortSectionItemPreview() {
    ShopAppTheme() {
        SortSectionItem(
            text = "Lowest price",
            selected = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun SortSectionItemNotSelectedPreview() {
    ShopAppTheme() {
        SortSectionItem(
            text = "Highest price",
            selected = false,
            onClick = {}
        )
    }
}