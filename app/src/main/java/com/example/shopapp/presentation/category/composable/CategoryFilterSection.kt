package com.example.shopapp.presentation.category.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.R
import com.example.shopapp.util.Category
import com.example.shopapp.util.Constants.CATEGORY_CHECKBOXES

@Composable
fun CategoryFilterSection(
    categoryFilterMap: Map<String,Boolean>,
    onCheckedChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .testTag(CATEGORY_CHECKBOXES),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            CategoryFilterItem(
                text = stringResource(id = R.string.men_clothing),
                isChecked = categoryFilterMap.getValue(Category.Men.title),
                onCheckedChange = { onCheckedChange(it) }
            )

            CategoryFilterItem(
                text = stringResource(id = R.string.women_clothing),
                isChecked = categoryFilterMap.getValue(Category.Women.title),
                onCheckedChange = { onCheckedChange(it) }
            )
        }

        Column() {
            CategoryFilterItem(
                text = stringResource(id = R.string.jewelery),
                isChecked = categoryFilterMap.getValue(Category.Jewelery.title),
                onCheckedChange = { onCheckedChange(it) }
            )

            CategoryFilterItem(
                text = stringResource(id = R.string.electronics),
                isChecked = categoryFilterMap.getValue(Category.Electronics.title),
                onCheckedChange = { onCheckedChange(it) }
            )
        }
    }
}

@Preview
@Composable
fun CategoryFilterSectionPreviewAllSelected() {
    val map = mapOf<String,Boolean>().withDefault { true }

    CategoryFilterSection(
        categoryFilterMap = map,
        onCheckedChange = {}
    )
}

@Preview
@Composable
fun CategoryFilterSectionPreviewNoneSelected() {
    val map = mapOf<String,Boolean>().withDefault { false }

    CategoryFilterSection(
        categoryFilterMap = map,
        onCheckedChange = {}
    )
}