package com.example.shopapp.presentation.cart.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.example.shopapp.util.Constants.ORDER_PLACED_DIALOG

@Composable
fun OrderPlacedDialog(
    onGoHome: () -> Unit
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(ORDER_PLACED_DIALOG)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.order_placed),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.thank_you),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )

                Spacer(modifier = Modifier.height(15.dp))

                NavigationButton(
                    text = stringResource(id = R.string.home),
                    onClick = { onGoHome() }
                )
            }
        }
    }
}

@Preview
@Composable
fun OrderPlacedPreview() {
    OrderPlacedDialog(
        onGoHome = {}
    )
}