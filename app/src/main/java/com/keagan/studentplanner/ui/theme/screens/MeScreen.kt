package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MeScreen() {
    Scaffold(topBar = { TopBar("Me") }) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(8.dp))
            Text("Keagan Shaw", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
            Text("keagan@example.com", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(16.dp))

            // weight() is used INSIDE RowScope
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCardSmall("42", "Tasks", modifier = Modifier.weight(1f))
                StatCardSmall("18", "Notes", modifier = Modifier.weight(1f))
                StatCardSmall("12h", "Focus", modifier = Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))
            SettingsItem("Account")
            SettingsItem("Notifications")
            SettingsItem("Theme")
            SettingsItem("Privacy")
            SettingsItem("Help & Support")
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = { }) { Text("Logout", color = MaterialTheme.colorScheme.error) }
        }
    }
}

@Composable
private fun StatCardSmall(value: String, label: String, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier) {
        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, style = MaterialTheme.typography.titleLarge)
            Text(label, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun SettingsItem(title: String) {
    ListItem(
        headlineContent = { Text(title) },
        trailingContent = { Text("›") }
    )
    HorizontalDivider()
}
