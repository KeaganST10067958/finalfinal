package com.keagan.studentplanner.navigation

sealed class Screen(val route: String, val label: String, val icon: String) {
    data object Dashboard : Screen("dashboard", "Dashboard", "🏠")
    data object Todo      : Screen("todo",      "To-Do",     "✅")
    data object Calendar  : Screen("calendar",  "Calendar",  "📅")
    data object Notes     : Screen("notes",     "Notes",     "📝")
    data object Me        : Screen("me",        "Me",        "😊")
}
val bottomScreens = listOf(Screen.Dashboard, Screen.Todo, Screen.Calendar, Screen.Notes, Screen.Me)
