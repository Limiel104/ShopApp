package com.example.shopapp.presentation.account.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.presentation.common.composable.BottomBar
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.ui.theme.ShopAppTheme

@Composable
fun AccountContent(
    customerName: String,
    customerClubPoints: Int
) {
    val scaffoldState = rememberScaffoldState()
    val coupons = listOf(10,20,50)

    Scaffold(
        topBar = { AccountTopBar(
            customerName = customerName
        ) },
        bottomBar = { BottomBar() },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PointsCard(
                customerClubPoints = customerClubPoints
            )

            Spacer(modifier = Modifier.height(30.dp))

            LazyRow(
            ) {
                itemsIndexed(coupons) { _, coupon ->
                    CouponItem(
                        discount = coupon,
                        pointsToActivate = coupon*10
                    )

                    Spacer(modifier = Modifier.width(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            ShopButtonItem(
                text = "My profile",
                testTag = "Orders button",
                onClick = {}
            )

            Spacer(modifier = Modifier.height(10.dp))

            ShopButtonItem(
                text = "Orders and Returns",
                testTag = "Orders button",
                onClick = {}
            )
        }
    }
}

@Preview
@Composable
fun AccountContentPreview() {
    ShopAppTheme {
        AccountContent(
            customerName = "John",
            customerClubPoints = 234
        )
    }
}