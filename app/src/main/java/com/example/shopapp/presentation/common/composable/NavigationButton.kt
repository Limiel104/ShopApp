package com.example.shopapp.presentation.common.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NavigationButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        colors = ButtonDefaults.buttonColors(Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp,9.dp)
            .testTag("$text button"),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondary),
        shape = RoundedCornerShape(35.dp),
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(7.dp)
        )
    }
}

@Preview
@Composable
fun NavigationButtonPreview() {
    NavigationButton(
        text = "Text",
        onClick = {}
    )
}