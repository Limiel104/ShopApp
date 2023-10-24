package com.example.shopapp.presentation.account.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.shopapp.util.Constants.ACCOUNT_POINTS_CARD

@Composable
fun PointsCard(
    userClubPoints: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .testTag(ACCOUNT_POINTS_CARD)
    ) {
        Column(
            modifier = Modifier
                //.background(Color.Green)
                .padding(15.dp),
            ) {
            Text(
                text = stringResource(id = R.string.shop_club),
                fontWeight = FontWeight.Light,
                fontSize = 10.sp
            )

            Text(
                text = "$userClubPoints " + stringResource(id = R.string.points),
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
            userClubPoints = 234
        )
    }
}