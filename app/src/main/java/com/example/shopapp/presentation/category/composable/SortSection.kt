package com.example.shopapp.presentation.category.composable

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
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CATEGORY_SORT_SECTION

@Composable
fun SortSection() {
    Column {
        Divider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(vertical = 10.dp)
                .testTag(CATEGORY_SORT_SECTION),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                SortSectionItem(
                    text = stringResource(id = R.string.name_ascending),
                    selected = true,
                    onClick = {}
                )

                Spacer(modifier = Modifier.height(10.dp))

                SortSectionItem(
                    text = stringResource(id = R.string.name_descending),
                    selected = false,
                    onClick = {}
                )
            }

            Column() {
                SortSectionItem(
                    text = stringResource(id = R.string.price_ascending),
                    selected = true,
                    onClick = {}
                )

                Spacer(modifier = Modifier.height(10.dp))

                SortSectionItem(
                    text = stringResource(id = R.string.price_descending),
                    selected = false,
                    onClick = {}
                )
            }
        }
    }
}

@Preview
@Composable
fun SortSectionPreview() {
    ShopAppTheme {
        SortSection()
    }
}