package com.example.shopapp.presentation.category.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.R
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun SortSectionItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .toggleable(
                value = selected,
                onValueChange = { onClick() }
            )
            .padding(end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            modifier = Modifier
                .size(25.dp)
                .testTag(text)
        )

        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Preview
@Composable
fun SortSectionItemPreview() {
    Surface() {
        ShopAppTheme() {
            SortSectionItem(
                text = stringResource(id = R.string.price_ascending),
                selected = true,
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
fun SortSectionItemNotSelectedPreview() {
    Surface() {
        ShopAppTheme() {
            SortSectionItem(
                text = stringResource(id = R.string.price_descending),
                selected = false,
                onClick = {}
            )
        }
    }
}