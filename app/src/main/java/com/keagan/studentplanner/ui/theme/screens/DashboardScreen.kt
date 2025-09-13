package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.keagan.studentplanner.ui.theme.*

@Composable
fun DashboardScreen() {
    Scaffold(topBar = { TopBar("Dashboard") }) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Stats row
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("42", "Tasks Done", Peach, Modifier.weight(1f))
                StatCard("18", "Notes", Mint, Modifier.weight(1f))
                StatCard("12h", "Focus", Blue, Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            // Quick actions
            Text("Quick actions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                QuickActionCard("New Task", "✍️", onClick = { /* open add task sheet */ }, Modifier.weight(1f))
                QuickActionCard("New Note", "📝", onClick = { /* open add note sheet */ }, Modifier.weight(1f))
                QuickActionCard("Focus 25m", "⏱️", onClick = { /* start focus */ }, Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            // Upcoming
            Text("Upcoming", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Fri • Essay draft", fontWeight = FontWeight.SemiBold)
                    Text("Due Friday", color = TextGrey, style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.height(8.dp))
                    Divider()
                    Spacer(Modifier.height(8.dp))
                    Text("Mon • CS Exam", fontWeight = FontWeight.SemiBold)
                    Text("Revise chapters 7–9", color = TextGrey, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Composable
private fun StatCard(value: String, label: String, color: androidx.compose.ui.graphics.Color, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(containerColor = color.copy(alpha = 0.35f))
    ) {
        Column(Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, style = MaterialTheme.typography.titleLarge)
            Text(label, style = MaterialTheme.typography.bodySmall, color = TextGrey)
        }
    }
}

@Composable
private fun QuickActionCard(title: String, emoji: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.elevatedCardColors(containerColor = PrimaryContainer)
    ) {
        Column(Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(4.dp))
            Text(title, style = MaterialTheme.typography.bodySmall)
        }
    }
}
