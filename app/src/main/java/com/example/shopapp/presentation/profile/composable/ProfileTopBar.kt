package com.example.shopapp.presentation.profile.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.R
import com.example.shopapp.presentation.common.Constants.GO_BACK_BTN
import com.example.shopapp.presentation.common.Constants.PROFILE_TOP_BAR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(
    onClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.profile)) },
        navigationIcon = {
            IconButton(
                onClick = { onClick() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = GO_BACK_BTN
                )
            }
        },
        modifier = Modifier.testTag(PROFILE_TOP_BAR)
    )
}

@Preview
@Composable
fun ProfileTopBarPreview() {
    ProfileTopBar(
        onClick = {}
    )
}