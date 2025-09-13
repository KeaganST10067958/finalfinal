package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.keagan.studentplanner.ui.theme.*

@Composable
fun DashboardScreen() {
    // pretend 75% tasks done today (UI only)
    val progress = 0.75f

    Scaffold(topBar = { TopBar("Dashboard") }) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            // Progress ring hero
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = PrimaryContainer)
            ) {
                Row(
                    Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ProgressRing(progress = progress, label = "Today")
                    Column(Modifier.weight(1f)) {
                        Text("Great pace!", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(6.dp))
                        Text("You’ve completed 6 of 8 tasks.", color = TextGrey, style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            AssistChip(onClick = {}, label = { Text("Start Focus 25m") })
                            AssistChip(onClick = {}, label = { Text("Add Task") })
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Stats row
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard("42", "Tasks Done", Peach, Modifier.weight(1f))
                StatCard("18", "Notes", Mint, Modifier.weight(1f))
                StatCard("12h", "Focus", Blue, Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            Text("Upcoming", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text("Fri • Essay draft", fontWeight = FontWeight.SemiBold)
                    Text("Due Friday", color = TextGrey, style = MaterialTheme.typography.bodySmall)
                    Spacer(Modifier.height(8.dp))
                    HorizontalDivider()
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
