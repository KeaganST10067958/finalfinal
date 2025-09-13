package com.keagan.studentplanner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.keagan.studentplanner.ui.theme.screens.DashboardScreen
import com.keagan.studentplanner.ui.theme.screens.TodoScreen
import com.keagan.studentplanner.ui.theme.screens.CalendarScreen
import com.keagan.studentplanner.ui.theme.screens.NotesScreen   // <-- add this
import com.keagan.studentplanner.ui.theme.screens.MeScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = "dashboard"
) {
    NavHost(navController = navController, startDestination = startDestination, modifier = modifier) {
        composable("dashboard") { DashboardScreen() }
        composable("todo") { TodoScreen() }
        composable("calendar") { CalendarScreen() }
        composable("notes") { NotesScreen() }   // <-- resolves now
        composable("me") { MeScreen() }
    }
}
