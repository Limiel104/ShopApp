package com.example.shopapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shopapp.composable.*
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
                    CategoryScreen()
                }
            }
        }
    }
}

@Composable
@Preview
fun ShopTextFieldItemPreview() {
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
fun ShopTextFieldItemWithErrorPreview() {
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

@Preview
@Composable
fun SignupScreenPreview() {
    ShopAppTheme() {
        SignupScreen()
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    ShopAppTheme {
        HomeScreen()
    }
}

@Preview
@Composable
fun HomeTopBarPreview() {
    ShopAppTheme {
        HomeTopBar()
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    ShopAppTheme {
        BottomBar()
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

@Preview
@Composable
fun OfferItemPreview() {
    ShopAppTheme {
        OfferItem(
            text = "All clothes for women now 10% cheaper"
        )
    }
}

@Composable
fun CategoryScreen() {
    val products = listOf(1,2,3,4)
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            CategoryTopBar(
                categoryName = "Category Name"
            ) },
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
                itemsIndexed(products) { _, product ->
                    ProductItem(

                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CategoryScreenPreview() {
    ShopAppTheme {
        CategoryScreen()
    }
}

@Composable
fun CategoryTopBar(
    categoryName: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = categoryName,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                imageVector = Icons.Outlined.Sort,
                contentDescription = "Sort",
                modifier = Modifier
                    .clickable {}
            )

            Spacer(modifier = Modifier.width(15.dp))

            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "Cart",
                modifier = Modifier
                    .clickable {}
            )
        }
    }
}

@Preview
@Composable
fun CategoryTopBarPreview() {
    ShopAppTheme {
        CategoryTopBar(
            categoryName = "Category Name"
        )
    }
}

@Composable
fun ProductItem() {
    Column() {
        Card(
            modifier = Modifier
                .size(150.dp,200.dp)
        ) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(R.drawable.ic_no_image)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_no_image)
                    .build(),
                contentDescription = "Image",
                fallback = painterResource(R.drawable.ic_no_image),
                error = painterResource(R.drawable.ic_no_image)
            )
        }
    }
}

@Preview
@Composable
fun ProductItemPreview() {
    ShopAppTheme {
        ProductItem()
    }
}