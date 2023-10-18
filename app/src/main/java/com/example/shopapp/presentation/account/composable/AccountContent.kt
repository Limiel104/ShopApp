package com.example.shopapp.presentation.account.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.shopapp.util.Constants.ACCOUNT_CONTENT
import com.example.shopapp.util.Constants.ACCOUNT_LAZY_ROW
import com.example.shopapp.util.Constants.MY_PROFILE_BTN
import com.example.shopapp.util.Constants.ORDERS_BTN
import com.example.shopapp.util.Constants.LOGOUT_BTN

@Composable
fun AccountContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    name: String,
    userPoints: Int,
    isCouponActivated: Boolean,
    onActivateCoupon: (Int) -> Unit,
    onLogout: () -> Unit,
    onGoToCart: () -> Unit,
    onGoToOrders: () -> Unit,
    onGoToProfile: () -> Unit
) {
    val coupons = listOf(10,20,50)

    Scaffold(
        topBar = {
            AccountTopBar(
                userName = name,
                onClick = { onGoToCart() }
            ) },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .testTag(ACCOUNT_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp)
        ) {
            PointsCard(
                userClubPoints = userPoints
            )

            Spacer(modifier = Modifier.height(30.dp))

            LazyRow(
                modifier = Modifier.testTag(ACCOUNT_LAZY_ROW)
            ) {
                itemsIndexed(coupons) { _, coupon ->
                    CouponItem(
                        discount = coupon,
                        pointsToActivate = coupon*100,
                        isActive = !isCouponActivated && ((userPoints+1) >= coupon*100),
                        onClick = { onActivateCoupon(it) }
                    )

                    Spacer(modifier = Modifier.width(20.dp))
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            ShopButtonItem(
                text = stringResource(id = R.string.my_profile),
                testTag = MY_PROFILE_BTN,
                onClick = { onGoToProfile() }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ShopButtonItem(
                text = stringResource(id = R.string.orders),
                testTag = ORDERS_BTN,
                onClick = { onGoToOrders() }
            )

            Spacer(modifier = Modifier.height(10.dp))

            ShopButtonItem(
                text = stringResource(id = R.string.logout),
                testTag = LOGOUT_BTN,
                onClick = { onLogout() }
            )
        }
    }
}

@Preview
@Composable
fun AccountContentCoupopNotActivatedPreview() {
    ShopAppTheme {
        AccountContent(
            scaffoldState = rememberScaffoldState(),
            bottomBarHeight = 56.dp,
            name = "John",
            userPoints = 2234,
            isCouponActivated = false,
            onActivateCoupon = {},
            onLogout = {},
            onGoToCart = {},
            onGoToOrders = {},
            onGoToProfile = {}
        )
    }
}

@Preview
@Composable
fun AccountContentCouponAlreadyActivatedPreview() {
    ShopAppTheme {
        AccountContent(
            scaffoldState = rememberScaffoldState(),
            bottomBarHeight = 56.dp,
            name = "John",
            userPoints = 2234,
            isCouponActivated = true,
            onActivateCoupon = {},
            onLogout = {},
            onGoToCart = {},
            onGoToOrders = {},
            onGoToProfile = {}
        )
    }
}