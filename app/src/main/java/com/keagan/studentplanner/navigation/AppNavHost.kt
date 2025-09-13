package com.keagan.studentplanner.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*

import com.keagan.studentplanner.ui.theme.screens.DashboardScreen
import com.keagan.studentplanner.ui.theme.screens.TodoScreen
import com.keagan.studentplanner.ui.theme.screens.CalendarScreen
import com.keagan.studentplanner.ui.theme.screens.NotesScreen
import com.keagan.studentplanner.ui.theme.screens.MeScreen

@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val current = backStack?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar(tonalElevation = 2.dp) {
                bottomScreens.forEach { s ->
                    val selected = current == s.route
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            if (!selected) {
                                navController.navigate(s.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = { Text(s.icon) },
                        label = { Text(s.label) }
                    )
                }
            }
        }
    ) { inner ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(inner)
        ) {
            composable(Screen.Dashboard.route) { DashboardScreen() }
            composable(Screen.Todo.route)      { TodoScreen() }
            composable(Screen.Calendar.route)  { CalendarScreen() }
            composable(Screen.Notes.route)     { NotesScreen() }
            composable(Screen.Me.route)        { MeScreen() }
        }
    }
}
