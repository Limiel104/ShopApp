package com.example.shopapp.presentation.category.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryFilterItem(
    text: String,
    isChecked: Boolean,
    onCheckedChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(end = 15.dp)
            .toggleable(
                value = isChecked,
                onValueChange = { onCheckedChange(text) }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckedChange(text) }
        )

        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Preview
@Composable
fun CategoryFilterItemPreviewIsChecked() {
    Surface() {
        CategoryFilterItem(
            text = "Men's clothing",
            isChecked = true,
            onCheckedChange = {}
        )
    }
}

@Preview
@Composable
fun CategoryFilterItemPreviewIsNotChecked() {
    Surface() {
        CategoryFilterItem(
            text = "Men's clothing",
            isChecked = false,
            onCheckedChange = {}
        )
    }
}