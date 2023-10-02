package com.example.shopapp.presentation.profil.composable

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapp.R
import com.example.shopapp.domain.model.User
import com.example.shopapp.presentation.common.composable.ErrorTextFieldItem
import com.example.shopapp.presentation.common.composable.ShopButtonItem
import com.example.shopapp.presentation.common.composable.ShopTextFieldItem
import com.example.shopapp.util.Constants.PROFILE_CITY_ERROR
import com.example.shopapp.util.Constants.PROFILE_CITY_TF
import com.example.shopapp.util.Constants.PROFILE_CONTENT
import com.example.shopapp.util.Constants.PROFILE_FIRSTNAME_ERROR
import com.example.shopapp.util.Constants.PROFILE_FIRSTNAME_TF
import com.example.shopapp.util.Constants.PROFILE_LASTNAME_ERROR
import com.example.shopapp.util.Constants.PROFILE_LASTNAME_TF
import com.example.shopapp.util.Constants.PROFILE_STREET_ERROR
import com.example.shopapp.util.Constants.PROFILE_STREET_TF
import com.example.shopapp.util.Constants.PROFILE_ZIP_CODE_ERROR
import com.example.shopapp.util.Constants.PROFILE_ZIP_CODE_TF
import com.example.shopapp.util.Constants.SAVE_BTN

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileContent(
    scaffoldState: ScaffoldState,
    bottomBarHeight: Dp,
    user: User,
    firstNameError: String?,
    lastNameError: String?,
    streetError: String?,
    cityError: String?,
    zipCodeError: String?,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onStreetChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onZipCodeChange: (String) -> Unit,
    onGoBack: () -> Unit,
    onSave: () -> Unit
    ) {
    Scaffold(
        topBar = {
            ProfileTopBar(
                onClick = { onGoBack() }
            ) },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
            .padding(bottom = bottomBarHeight)
            .testTag(PROFILE_CONTENT)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            ShopTextFieldItem(
                text = user.firstName,
                label = stringResource(id = R.string.first_name),
                placeholder = stringResource(id = R.string.first_name),
                testTag = PROFILE_FIRSTNAME_TF,
                isError = firstNameError != null,
                onValueChange = { onFirstNameChange(it) }
            )

            if(firstNameError != null) {
                ErrorTextFieldItem(
                    errorMessage = firstNameError,
                    testTag = PROFILE_FIRSTNAME_ERROR
                )
            }

            ShopTextFieldItem(
                text = user.lastName,
                label = stringResource(id = R.string.last_name),
                placeholder = stringResource(id = R.string.last_name),
                testTag = PROFILE_LASTNAME_TF,
                isError = lastNameError != null,
                onValueChange = { onLastNameChange(it) }
            )

            if(lastNameError != null) {
                ErrorTextFieldItem(
                    errorMessage = lastNameError,
                    testTag = PROFILE_LASTNAME_ERROR
                )
            }

            ShopTextFieldItem(
                text = user.address.street,
                label = stringResource(id = R.string.street),
                placeholder = stringResource(id = R.string.street),
                testTag = PROFILE_STREET_TF,
                isError = streetError != null,
                onValueChange = { onStreetChange(it) }
            )

            if(streetError != null) {
                ErrorTextFieldItem(
                    errorMessage = streetError,
                    testTag = PROFILE_STREET_ERROR
                )
            }

            Row() {
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    ShopTextFieldItem(
                        text = user.address.city,
                        label = stringResource(id = R.string.city),
                        placeholder = stringResource(id = R.string.city),
                        testTag = PROFILE_CITY_TF,
                        isError = cityError != null,
                        onValueChange = { onCityChange(it) }
                    )

                    if(cityError != null) {
                        ErrorTextFieldItem(
                            errorMessage = cityError,
                            testTag = PROFILE_CITY_ERROR
                        )
                    }
                }

                Spacer(modifier = Modifier.width(5.dp))

                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    ShopTextFieldItem(
                        text = user.address.zipCode,
                        label = stringResource(id = R.string.zip_code),
                        placeholder = stringResource(id = R.string.zip_code),
                        testTag = PROFILE_ZIP_CODE_TF,
                        isError = zipCodeError != null,
                        onValueChange = { onZipCodeChange(it) }
                    )

                    if(zipCodeError != null) {
                        ErrorTextFieldItem(
                            errorMessage = zipCodeError,
                            testTag = PROFILE_ZIP_CODE_ERROR
                        )
                    }
                }
            }
            ShopButtonItem(
                text = stringResource(id = R.string.save),
                testTag = SAVE_BTN,
                onClick = { onSave() }
            )
        }
    }
}

@Preview
@Composable
fun ProfileContentPreview() {

}