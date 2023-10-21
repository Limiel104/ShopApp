package com.example.shopapp.presentation.common.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun ShopTextFieldItem(
    text: String,
    label: String,
    placeholder: String,
    testTag: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = text,
        singleLine = true,
        isError = isError,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        onValueChange = { onValueChange(it) },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
    )
}

@Composable
@Preview
fun ShopTextFieldItemPreview() {
    Surface() {
        ShopAppTheme {
            ShopTextFieldItem(
                text = "",
                label = "text",
                placeholder = "placeholder",
                testTag = "tag",
                isError = false,
                onValueChange = {}
            )
        }
    }
}

@Composable
@Preview
fun ShopTextFieldItemWithErrorPreview() {
    Surface() {
        ShopAppTheme {
            ShopTextFieldItem(
                text = "",
                label = "text",
                placeholder = "placeholder",
                testTag = "tag",
                isError = true,
                onValueChange = {}
            )
        }
    }
}