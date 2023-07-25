package com.example.shopapp.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun ErrorTextFieldItem(
    errorMessage: String,
    testTag: String
) {
    Text(
        text = errorMessage,
        fontSize = 13.sp,
        color = MaterialTheme.colors.error,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .testTag(testTag)
            .background(MaterialTheme.colors.background)
    )
}

@Preview
@Composable
fun ErrorTextFieldItemPreview() {
    ShopAppTheme {
        ErrorTextFieldItem(
            errorMessage = "Error",
            testTag = "tag"
        )
    }
}