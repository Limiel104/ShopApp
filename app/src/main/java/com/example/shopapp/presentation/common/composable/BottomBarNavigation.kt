package com.example.shopapp.presentation.common.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shopapp.domain.model.BottomBarItem

@Composable
fun BottomBarNavigation(
    items: List<BottomBarItem>,
    navController: NavController,
    onItemClick: (BottomBarItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if(item.badgeCount>0) {
                            BadgedBox(
                                badge = {
                                    Badge {
                                        Text(item.badgeCount.toString())
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )

                                Text(
                                    text = item.name,
                                    fontSize = 10.sp
                                )
                            }
                        }
                        else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )

                            Text(
                                text = item.name,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
        }
    }
}