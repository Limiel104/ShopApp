package com.example.shopapp.presentation.profile.composable

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.R
import com.example.shopapp.util.Constants.GO_BACK_BTN
import com.example.shopapp.util.Constants.PROFILE_TOP_BAR

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