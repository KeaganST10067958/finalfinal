package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, actions: @Composable () -> Unit = {}) {
    CenterAlignedTopAppBar(
        title = { Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold) },
        actions = { actions() }
    )
}

@Composable
fun SearchAction(onClick: () -> Unit = {}) {
    IconButton(onClick = onClick) { Text("🔍") }
}

@Composable
fun AddAction(onClick: () -> Unit = {}) {
    IconButton(onClick = onClick) { Text("➕") }
}
