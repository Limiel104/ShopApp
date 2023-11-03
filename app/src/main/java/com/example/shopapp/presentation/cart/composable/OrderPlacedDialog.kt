package com.example.shopapp.presentation.cart.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.R
import com.example.shopapp.util.Constants.ORDER_PLACED_DIALOG

@Composable
fun OrderPlacedDialog(
    onGoHome: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.order_placed))
        },
        text = {
            Text(text = stringResource(id = R.string.thank_you))
        },
        onDismissRequest = {
            onGoHome()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onGoHome()
                }
            ) {
                Text(text = stringResource(id = R.string.go_back))
            }
        },
        dismissButton = {},
        modifier = Modifier.testTag(ORDER_PLACED_DIALOG)
    )
}

@Preview
@Composable
fun OrderPlacedPreview() {
    Surface() {
        OrderPlacedDialog(
            onGoHome = {}
        )
    }
}