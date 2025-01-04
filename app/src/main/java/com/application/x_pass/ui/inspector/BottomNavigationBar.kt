package com.application.x_pass.ui.inspector

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.application.x_pass.R

@Composable
fun BottomNavigationBar(
    currentDestination: String,
    onNavigateToMain: () -> Unit,
    onNavigateToHistory: () -> Unit,
) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(56.dp)
            .shadow(4.dp, shape = MaterialTheme.shapes.medium),
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home_item),
                    contentDescription = "main_inspector",
                    tint = if (currentDestination == "main_inspector") Color(0xffF35421) else Color.Gray
                )
            },
            selected = currentDestination == "main_inspector",
            onClick = onNavigateToMain,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.history),
                    contentDescription = "history_inspector",
                    tint = if (currentDestination == "history_inspector") Color(0xffF35421) else Color.Gray // Меняем цвет иконки
                )
            },
            selected = currentDestination == "history_inspector",
            onClick = onNavigateToHistory,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )
    }
}
