package com.example.shopapp.presentation.category.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.util.Constants.CATEGORY_FILTER_SECTION

@Composable
fun FilterSection(
    sliderPosition: ClosedFloatingPointRange<Float>,
    sliderRange: ClosedFloatingPointRange<Float>,
    isCategoryFilterVisible: Boolean,
    categoryFilterMap: Map<String,Boolean>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onCheckedChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(bottom = 20.dp)
            .testTag(CATEGORY_FILTER_SECTION),
    ) {
        Divider()

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.price),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Light
        )

        PriceSlider(
            sliderPosition = sliderPosition,
            sliderRange = sliderRange,
            onValueChange = { onValueChange(it) }
        )

        Divider()

        if(isCategoryFilterVisible) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.category),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Light
            )

            CategoryFilterSection(
                categoryFilterMap = categoryFilterMap,
                onCheckedChange = { onCheckedChange(it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Divider()
        }
    }
}

@Preview
@Composable
fun FilterSectionPreviewCategoryFilterVisible() {
    FilterSection(
        sliderPosition = 1f..4f,
        sliderRange = 0f..5f,
        isCategoryFilterVisible = true,
        categoryFilterMap = mapOf(),
        onValueChange = {},
        onCheckedChange = {}
    )
}

@Preview
@Composable
fun FilterSectionPreviewCategoryFilterNotVisible() {
    FilterSection(
        sliderPosition = 1f..4f,
        sliderRange = 0f..5f,
        isCategoryFilterVisible = false,
        categoryFilterMap = mapOf(),
        onValueChange = {},
        onCheckedChange = {}
    )
}