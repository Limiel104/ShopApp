package com.example.shopapp.presentation.cart.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.R
import com.example.shopapp.util.Constants.CART_TOTAL_AMOUNT_COLUMN
import com.example.shopapp.util.Constants.CART_TOTAL_AMOUNT_SECTION
import com.example.shopapp.util.Constants.ORDER_BTN

@Composable
fun TotalAmountSection(
    totalAmount: Double,
    onOrderPlaced: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 10.dp)
            .testTag(CART_TOTAL_AMOUNT_SECTION),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .semantics { contentDescription = CART_TOTAL_AMOUNT_COLUMN },
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.total_amount),
                fontWeight = FontWeight.Light,
                fontSize = 20.sp
            )

            Text(
                text = String.format("%.2f PLN",totalAmount).replace(".",","),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }

        Button(
            modifier = Modifier
                .testTag(ORDER_BTN),
            onClick = { onOrderPlaced() }
        ) {
            Text(text = stringResource(id = R.string.order))
        }
    }

}

@Preview
@Composable
fun TotalAmountSectionPreview() {
    Surface() {
        TotalAmountSection(
            totalAmount = 155.45,
            onOrderPlaced = {}
        )
    }
}