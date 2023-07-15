package com.example.shopapp.presentation.account.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.R
import com.example.shopapp.util.Constants.ACCOUNT_LAZY_ROW
import com.example.shopapp.util.Constants.MY_PROFILE_BTN
import com.example.shopapp.util.Constants.ORDERS_AND_RETURNS_BTN

@Composable
fun AccountContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    userName: String,
    userClubPoints: Int
) {
    val coupons = listOf(10,20,50)

    Scaffold(
        topBar = { AccountTopBar(
            userName = userName
        ) },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
            .padding(bottom = bottomBarHeight)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PointsCard(
                userClubPoints = userClubPoints
            )

            Spacer(modifier = Modifier.height(30.dp))

            LazyRow(
                modifier = Modifier.testTag(ACCOUNT_LAZY_ROW)
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
                text = stringResource(id = R.string.my_profile),
                testTag = MY_PROFILE_BTN,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(10.dp))

            ShopButtonItem(
                text = stringResource(id = R.string.orders_and_returns),
                testTag = ORDERS_AND_RETURNS_BTN,
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
            scaffoldState = rememberScaffoldState(),
            bottomBarHeight = 56.dp,
            userName = "John",
            userClubPoints = 234
        )
    }
}