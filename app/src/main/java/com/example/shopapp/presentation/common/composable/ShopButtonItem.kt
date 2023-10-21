package com.example.shopapp.presentation.common.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun ShopButtonItem(
    text: String,
    size: Int = 0,
    testTag: String,
    onClick: () -> Unit
) {
    if(size == 0) {
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
            onClick = { onClick() }
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .padding(7.dp)
            )
        }
    }
    else {
        OutlinedButton(
            modifier = Modifier
                .width(size.dp)
                .testTag(testTag),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
            onClick = { onClick() }
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier
                    .padding(7.dp)
            )
        }
    }
}

@Preview
@Composable
fun ShopButtonItemPreview() {
    ShopAppTheme {
        ShopButtonItem(
            text = "text",
            testTag = "tag",
            onClick = {}
        )
    }
}