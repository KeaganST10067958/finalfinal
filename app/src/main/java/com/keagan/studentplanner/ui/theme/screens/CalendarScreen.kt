package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CalendarScreen() {
    Scaffold(topBar = { TopBar("Calendar", actions = { AddAction() }) }) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Month header
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("September 2025", style = MaterialTheme.typography.titleLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(onClick = { /* prev */ }) { Text("‹") }
                    OutlinedButton(onClick = { /* next */ }) { Text("›") }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Placeholder monthly grid – UI only
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Month grid placeholder", color = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
                    Text("Tap days to view events…", color = MaterialTheme.colorScheme.outline)
                }
            }
        }
    }
}
