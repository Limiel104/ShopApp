package com.example.shopapp.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun OfferItem(
    text: String
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .padding(top = 5.dp, bottom = 20.dp)
            .fillMaxWidth()
            .height(150.dp),
        backgroundColor = Color.Gray
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun OfferItemPreview() {
    ShopAppTheme {
        OfferItem(
            text = "All clothes for women now 10% cheaper"
        )
    }
}