package com.example.shopapp.presentation.category.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.util.Constants.CATEGORY_PRICE_SLIDER
import com.example.shopapp.util.Constants.CATEGORY_PRICE_SLIDER_ITEM

@Composable
fun PriceSlider(
    sliderPosition: ClosedFloatingPointRange<Float>,
    sliderRange: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit
) {
    Column(
        modifier = Modifier
            .testTag(CATEGORY_PRICE_SLIDER),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = String.format("%.2f",sliderPosition.start) + " - " + String.format("%.2f",sliderPosition.endInclusive)
        )

        RangeSlider(
            modifier = Modifier
                .testTag(CATEGORY_PRICE_SLIDER_ITEM),
            value = sliderPosition,
            valueRange = sliderRange,
            onValueChange = { onValueChange(it) }
        )
    }
}

@Preview
@Composable
fun PriceSliderPreview() {
    Surface() {
        PriceSlider(
            sliderPosition = 1f..3f,
            sliderRange = 0f..10f,
            onValueChange = {}
        )
    }
}