package com.keagan.studentplanner.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Description // notes icon
import androidx.compose.material.icons.outlined.Person

@Composable
fun AppScaffold() {
    val navController = rememberNavController()

    val items = listOf(
        NavItem("dashboard", "Dashboard", Icons.Outlined.Home),
        NavItem("todo", "To-Do", Icons.Outlined.CheckCircle),
        NavItem("calendar", "Calendar", Icons.Outlined.CalendarMonth),
        NavItem("notes", "Notes", Icons.Outlined.Description),
        NavItem("me", "Me", Icons.Outlined.Person),
    )
    var current by remember { mutableStateOf(items.first().route) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = current == item.route,
                        onClick = {
                            current = item.route
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                            }
                        },
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { inner ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(inner),
            startDestination = items.first().route
        )
    }
}

private data class NavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
