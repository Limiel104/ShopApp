package com.example.shopapp.domain.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)