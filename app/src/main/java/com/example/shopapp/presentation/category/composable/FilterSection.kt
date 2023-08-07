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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R

@Composable
fun FilterSection(
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(bottom = 20.dp),
    ) {
        Divider()

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.price),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Light
        )

        PriceSlider()

        Divider()
    }
}

@Preview
@Composable
fun FilterSectionPreview() {
    FilterSection()
}