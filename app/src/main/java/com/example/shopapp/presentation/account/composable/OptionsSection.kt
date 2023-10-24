package com.example.shopapp.presentation.account.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.util.Constants.LOGOUT_BTN
import com.example.shopapp.util.Constants.MY_PROFILE_BTN
import com.example.shopapp.util.Constants.ORDERS_BTN

@Composable
fun OptionsSection(
    onGoToProfile: () -> Unit,
    onGoToOrders: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1F)
                    .clickable { onGoToProfile() }
                    .testTag(MY_PROFILE_BTN)
            ) {
                Text(
                    text = stringResource(id = R.string.my_profile),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                )
            }

            Card(
                modifier = Modifier
                    .weight(1F)
                    .clickable { onGoToOrders() }
                    .testTag(ORDERS_BTN)
            ) {
                Text(
                    text = stringResource(id = R.string.orders),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
        ) {
            Card(
                modifier = Modifier
                    .weight(1F)
                    .clickable { onLogout() }
                    .testTag(LOGOUT_BTN)
            ) {
                Text(
                    text = stringResource(id = R.string.logout),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun OptionsSectionPreview() {
    Surface() {
        OptionsSection(
            onGoToProfile = {},
            onGoToOrders = {},
            onLogout = {}
        )
    }
}