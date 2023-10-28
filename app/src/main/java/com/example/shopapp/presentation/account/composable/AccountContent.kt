package com.example.shopapp.presentation.account.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.ACCOUNT_CONTENT
import com.example.shopapp.util.Constants.ACCOUNT_LAZY_ROW

@Composable
fun AccountContent(
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
        modifier = Modifier
            .fillMaxSize()
            .testTag(ACCOUNT_CONTENT)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            PointsCard(
                userClubPoints = userPoints
            )

            LazyRow(
                modifier = Modifier.testTag(ACCOUNT_LAZY_ROW)
            ) {
                itemsIndexed(coupons) { _, coupon ->
                    CouponItem(
                        discount = coupon,
                        pointsToActivate = coupon*100,
                        isActive = !isCouponActivated && (userPoints >= coupon*100),
                        onClick = { onActivateCoupon(it) }
                    )

                    Spacer(modifier = Modifier.width(20.dp))
                }
            }

            OptionsSection(
                onGoToProfile = { onGoToProfile() },
                onGoToOrders = { onGoToOrders() },
                onLogout = { onLogout() }
            )
        }
    }
}

@Preview
@Composable
fun AccountContentCouponNotActivatedPreview() {
    ShopAppTheme {
        AccountContent(
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