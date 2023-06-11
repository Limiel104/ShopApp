package com.example.shopapp.presentation.account.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun CouponItem(
    discount: Int,
    pointsToActivate: Int
) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .clip(RoundedCornerShape(25.dp))
    ) {
        Column(
            modifier = Modifier
                .background(Color.Cyan)
                .padding(15.dp)
        ) {
            Text(
                text = "$discount PLN Discount",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )

            Text(
                text = "The coupon is valid for 14 days after activation.\n" +
                        "Minimum purchase amount is " + discount+1,
                fontWeight = FontWeight.Light,
                fontSize = 10.sp,
                modifier = Modifier
                    .padding(bottom = 20.dp)
            )

            ShopButtonItem(
                text = "Activate for $pointsToActivate",
                testTag = "Activate coupon button",
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
fun CouponItemPreview() {
    ShopAppTheme() {
        CouponItem(
            discount = 10,
            pointsToActivate = 100
        )
    }
}