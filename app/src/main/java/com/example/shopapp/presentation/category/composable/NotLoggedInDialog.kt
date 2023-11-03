package com.example.shopapp.presentation.category.composable

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
import com.example.shopapp.util.Constants.NOT_LOGGED_IN_DIALOG

@Composable
fun NotLoggedInDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.dialog_title))
        },
        text = {
            Text(text = stringResource(id = R.string.dialog_text))
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {},
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = stringResource(id = R.string.dismiss))
            }
        },
        modifier = Modifier.testTag(NOT_LOGGED_IN_DIALOG)
    )
}

@Preview
@Composable
fun NotLoggedInDialogPreview() {
    Surface() {
        NotLoggedInDialog(
            onDismiss = {}
        )
    }
}