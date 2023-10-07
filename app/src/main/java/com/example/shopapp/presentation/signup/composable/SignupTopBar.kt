package com.example.shopapp.presentation.signup.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.presentation.common.composable.IconButtonCard
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.GO_BACK_BTN
import com.example.shopapp.util.Constants.SIGNUP_TOP_BAR

@Composable
fun SignupTopBar(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(vertical = 15.dp)
            .testTag(SIGNUP_TOP_BAR),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        IconButtonCard(
            icon = Icons.Outlined.ArrowBack,
            description = GO_BACK_BTN,
            outsidePaddingValue = 0,
            onClick = { onClick() }
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = stringResource(id = R.string.signup),
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun SignupTopBarPreview() {
    ShopAppTheme {
        SignupTopBar(
            onClick = {}
        )
    }
}