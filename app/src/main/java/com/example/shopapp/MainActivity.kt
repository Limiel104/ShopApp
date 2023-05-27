package com.example.shopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapp.ui.theme.ShopAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun ShopTextFieldItem(
    text: String,
    label: String,
    placeholder: String,
    testTag: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = text,
        singleLine = true,
        isError = isError,
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        onValueChange = { onValueChange(it) },
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .testTag(testTag)
    )
}

@Composable
@Preview
fun ChatTextFieldItemPreview() {
    ShopAppTheme {
        ShopTextFieldItem(
            text = "",
            label = "Text",
            placeholder = "Placeholder",
            testTag = "testTag",
            isError = false,
            onValueChange = {}
        )
    }
}

@Composable
@Preview
fun ChatTextFieldItemWithErrorPreview() {
    ShopAppTheme {
        ShopTextFieldItem(
            text = "",
            label = "Text",
            placeholder = "Placeholder",
            testTag = "testTag",
            isError = true,
            onValueChange = {}
        )
    }
}

@Composable
fun ShopButtonItem(
    text: String,
    testTag: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.secondary),
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier
                .padding(7.dp)
        )
    }
}

@Preview
@Composable
fun ShopButtonItemPreview() {
    ShopAppTheme {
        ShopButtonItem(
            text = "Text",
            testTag = "testTag",
            onClick = {}
        )
    }
}

@Composable
fun ErrorTextFieldItem(
    errorMessage: String,
    testTag: String
) {
    Text(
        text = errorMessage,
        fontSize = 13.sp,
        color = MaterialTheme.colors.error,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .testTag(testTag)
    )
}

@Preview
@Composable
fun ErrorTextFieldItemPreview() {
    ShopAppTheme {
        ErrorTextFieldItem(
            errorMessage = "Error message",
            testTag = "tag"
        )
    }
}

@Composable
fun LoginScreen() {

    val email = "email@email.com"
    val emailError = ""
    val password = "password"
    val passwordError = ""
    val isLoading = false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Hello",
            color = MaterialTheme.colors.secondary,
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold
        )

        Column {
            ShopTextFieldItem(
                text = email,
                label = "email",
                placeholder = "placeholder",
                testTag = "email",
                isError = emailError != null,
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            if(emailError != null) {
                ErrorTextFieldItem(
                    errorMessage = emailError,
                    testTag = "errorTag"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ShopTextFieldItem(
                text = password,
                label = "password",
                placeholder = "placeholder",
                testTag = "tag",
                isError = passwordError != null,
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            if(passwordError != null) {
                ErrorTextFieldItem(
                    errorMessage = passwordError,
                    testTag = "tag"
                )
            }
        }

        ShopButtonItem(
            text = "Login",
            testTag = "tag",
            onClick = {}
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account?"
            )

            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = "Sign up",
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable {}
                    .testTag("tag")
            )
        }
    }

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag("tag"),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    ShopAppTheme() {
        LoginScreen()
    }
}

@Composable
fun SignupScreen() {

    val email = "email@email.com"
    val emailError = ""
    val password = "password"
    val passwordError = ""
    val confirmPassword = "confirmPassword"
    val confirmPasswordError = ""
    val isLoading = false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Create an account",
            fontSize = 32.sp,
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.SemiBold
        )

        Column() {
            ShopTextFieldItem(
                text = email,
                label = "email",
                placeholder = "placeholder",
                testTag = "tag",
                isError = emailError != null,
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )

            if(emailError != null) {
                ErrorTextFieldItem(
                    errorMessage = emailError,
                    testTag = "tag"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ShopTextFieldItem(
                text = password,
                label = "password",
                placeholder = "placeholder",
                testTag = "tag",
                isError = passwordError != null,
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            if(passwordError != null) {
                ErrorTextFieldItem(
                    errorMessage = passwordError,
                    testTag = "tag"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            ShopTextFieldItem(
                text = confirmPassword,
                label = "confirmPassword",
                placeholder = "placeholder",
                testTag = "tag",
                isError = confirmPasswordError != null,
                onValueChange = {},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )

            if(confirmPasswordError != null) {
                ErrorTextFieldItem(
                    errorMessage = confirmPasswordError,
                    testTag = "tag"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
        }
    }

    if(isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .testTag("tag"),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun SignupScreenPreview() {
    ShopAppTheme() {
        SignupScreen()
    }
}

@Composable
fun HomeScreen() {
    val offers = listOf(
        "All clothes for women now 10% cheaper",
        "All clothes for men now 15% cheaper",
        "All shirts 20% cheaper with code SHIRT20",
        "Buy two pairs of pants for the price of one",
        "13% off for purchase above 200$"
    )
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = { HomeTopBar() },
        bottomBar = { BottomBar() },
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 10.dp)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                itemsIndexed(offers) { _, offer ->
                    OfferItem(
                        text = offer
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    ShopAppTheme {
        HomeScreen()
    }
}

@Composable
fun HomeTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Shop Name",
            fontWeight = FontWeight.SemiBold
        )

        IconButton(
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "Cart"
            )
        }
    }
}

@Preview
@Composable
fun HomeTopBarPreview() {
    ShopAppTheme {
        HomeTopBar()
    }
}

@Composable
fun BottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomBarItem(
            text = "Home",
            icon = Icons.Outlined.Home,
            onClick = {}
        )

        BottomBarItem(
            text = "Categories",
            icon = Icons.Outlined.Search,
            onClick = {}
        )

        BottomBarItem(
            text = "Favourites",
            icon = Icons.Outlined.FavoriteBorder,
            onClick = {}
        )

        BottomBarItem(
            text = "Account",
            icon = Icons.Outlined.Person,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    ShopAppTheme {
        BottomBar()
    }
}

@Composable
fun BottomBarItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text
        )

        Text(
            text = text,
            fontSize = 10.sp
        )
    }
}

@Preview
@Composable
fun BottomBarItemPreview() {
    ShopAppTheme {
        BottomBarItem(
            text = "Favourites",
            icon = Icons.Outlined.FavoriteBorder,
            onClick = {}
        )
    }
}

@Composable
fun OfferItem(
    text: String
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .padding(top = 5.dp, bottom = 20.dp)
            .fillMaxWidth()
            .height(150.dp),
        backgroundColor = Color.Gray
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun OfferItemPreview() {
    ShopAppTheme {
        OfferItem(
            text = "All clothes for women now 10% cheaper"
        )
    }
}