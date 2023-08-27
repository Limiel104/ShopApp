package com.example.shopapp.presentation.category.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.shopapp.R
import com.example.shopapp.presentation.common.composable.NavigationButton
import com.example.shopapp.util.Constants.NOT_LOGGED_IN_DIALOG

@Composable
fun NotLoggedInDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(NOT_LOGGED_IN_DIALOG)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.dialog_text),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(15.dp))

                NavigationButton(
                    text = stringResource(id = R.string.dismiss),
                    onClick = { onDismiss() }
                )
            }
        }
    }
}

@Preview
@Composable
fun NotLoggedInDialogPreview() {
    NotLoggedInDialog(
        onDismiss = {}
    )
}