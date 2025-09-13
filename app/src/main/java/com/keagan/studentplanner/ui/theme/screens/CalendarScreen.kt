package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.YearMonth

@Composable
fun CalendarScreen() {
    var ym by remember { mutableStateOf(YearMonth.now()) }

    Scaffold(topBar = { TopBar("Calendar") }) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedButton(onClick = { ym = ym.minusMonths(1) }) { Text("‹") }
                Text(ym.month.name.lowercase().replaceFirstChar { it.titlecase() } + " ${ym.year}", style = MaterialTheme.typography.titleLarge)
                OutlinedButton(onClick = { ym = ym.plusMonths(1) }) { Text("›") }
            }

            Spacer(Modifier.height(12.dp))
            CalendarMonth(month = ym)
        }
    }
}
