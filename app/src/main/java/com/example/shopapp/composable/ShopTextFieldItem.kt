package com.example.shopapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .testTag(testTag)
    )
}

@Composable
@Preview
fun ShopTextFieldItemPreview() {
    ShopAppTheme {
        ShopTextFieldItem(
            text = "",
            label = "Text",
            placeholder = "Placeholder",
            testTag = "testTag",
            isError = false,
            onValueChange = {}
        )
    }
}

@Composable
@Preview
fun ShopTextFieldItemWithErrorPreview() {
    ShopAppTheme {
        ShopTextFieldItem(
            text = "",
            label = "Text",
            placeholder = "Placeholder",
            testTag = "testTag",
            isError = true,
            onValueChange = {}
        )
    }
}