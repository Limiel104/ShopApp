package com.example.shopapp.presentation.favourites.composable

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.shopapp.R
import com.example.shopapp.ui.theme.ShopAppTheme
import com.example.shopapp.util.Constants.CART_BTN
import com.example.shopapp.util.Constants.FAVOURITES_TOP_BAR

@Composable
fun FavouriteTopBar(
    onClick: () -> Unit
) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.favourite_title)) },
        actions = {
            IconButton(
                onClick = { onClick() }
            ) {
                  Icon(
                      imageVector = Icons.Outlined.ShoppingCart,
                      contentDescription = CART_BTN
                  )
            }
        },
        modifier = Modifier.testTag(FAVOURITES_TOP_BAR)
    )
}

@Preview
@Composable
fun FavouriteTopBarPreview() {
    ShopAppTheme {
        FavouriteTopBar(
            onClick = {}
        )
    }
}