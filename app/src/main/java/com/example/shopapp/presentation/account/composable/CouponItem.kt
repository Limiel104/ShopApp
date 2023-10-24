package com.example.shopapp.presentation.account.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.R
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.ACTIVATE_COUPON_BTN
import com.example.shopapp.util.Constants.COUPON_ITEM_

@Composable
fun CouponItem(
    discount: Int,
    pointsToActivate: Int,
    isActive: Boolean,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .clip(RoundedCornerShape(25.dp))
            .testTag(COUPON_ITEM_ + discount)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            Text(
                text = "$discount " + stringResource(id = R.string.pln_discount),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )

            Text(
                text = stringResource(id = R.string.coupon_validation_message) +
                        stringResource(id = R.string.purchase_minimal_amount_message) + " " + (discount+1),
                fontWeight = FontWeight.Light,
                fontSize = 10.sp,
                modifier = Modifier
                    .padding(bottom = 15.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .testTag(ACTIVATE_COUPON_BTN),
                    enabled = isActive,
                    onClick = { onClick(discount) }
                ) {
                    Text(
                        text = stringResource(id = R.string.activation_points_amount) + " $pointsToActivate",
                        modifier = Modifier
                            .padding(7.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CouponItemIsActivePreview() {
    ShopAppTheme() {
        CouponItem(
            discount = 10,
            pointsToActivate = 100,
            isActive = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun CouponItemIsNotActivePreview() {
    ShopAppTheme() {
        CouponItem(
            discount = 10,
            pointsToActivate = 100,
            isActive = false,
            onClick = {}
        )
    }
}