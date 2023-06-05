package com.example.shopapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun PointsCard(
    customerClubPoints: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
    ) {
        Column(
            modifier = Modifier
                .background(Color.Green)
                .padding(15.dp),

            ) {
            Text(
                text = "Shop Club",
                fontWeight = FontWeight.Light,
                fontSize = 10.sp
            )

            Text(
                text = "$customerClubPoints points",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }
    }
}

@Preview
@Composable
fun PointsCardPreview() {
    ShopAppTheme() {
        PointsCard(
            customerClubPoints = 234
        )
    }
}