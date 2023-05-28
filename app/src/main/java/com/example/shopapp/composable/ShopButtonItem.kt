package com.example.shopapp.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun ShopButtonItem(
    text: String,
    testTag: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier
                .padding(7.dp)
        )
    }
}

@Preview
@Composable
fun ShopButtonItemPreview() {
    ShopAppTheme {
        ShopButtonItem(
            text = "Text",
            testTag = "testTag",
            onClick = {}
        )
    }
}