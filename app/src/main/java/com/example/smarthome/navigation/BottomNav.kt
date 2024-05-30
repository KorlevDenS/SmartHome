package com.example.smarthome.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val listItems = listOf(
        BottomItem.Screen1,
        BottomItem.Screen2,
        BottomItem.Screen3,
        BottomItem.Screen4,
        BottomItem.Screen5
    )

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        listItems.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.route,
                onClick = {
                          navController.navigate(item.route)
                },
                icon = {
                    Icon(
                        modifier = Modifier.padding(
                            start = 20.dp, end = 20.dp, top = 6.dp),
                        painter = painterResource(id = item.iconId),
                        contentDescription = "Icon",
                        tint = when (currentRoute) {
                            item.route -> Color.Blue
                            else -> Color.Gray
                        }
                    )
                },
                label = {
                    Text(
                        modifier = Modifier.padding(bottom = 6.dp),
                        text = item.title,
                        fontSize = 12.sp,
                        color = when (currentRoute) {
                            item.route -> Color.Blue
                            else -> Color.Gray
                        }
                    )
                },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray
            )
        }
    }
}