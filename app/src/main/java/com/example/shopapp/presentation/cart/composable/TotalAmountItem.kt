package com.example.shopapp.presentation.cart.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.R
import com.example.shopapp.util.Constants.CART_TOTAL_AMOUNT_ROW

@Composable
fun TotalAmountItem(
    totalAmount: Double
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .testTag(CART_TOTAL_AMOUNT_ROW),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.total_amount),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )

        Text(
            text = String.format("%.2f PLN",totalAmount).replace(".",","),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
        )
    }
}

@Preview
@Composable
fun TotalAmountPreview() {
    Surface() {
        TotalAmountItem(
            totalAmount = 155.45
        )
    }
}