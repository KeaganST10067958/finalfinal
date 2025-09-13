package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.keagan.studentplanner.model.TaskUI

@Composable
fun TodoScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }     // 0=All 1=Active 2=Done
    var showAdd by remember { mutableStateOf(false) }

    val tasks = remember {
        mutableStateListOf(
            TaskUI("Revise Chemistry", "Flashcards 20min", done = false, tag = "study"),
            TaskUI("Buy snacks", "For study group", done = true,  tag = "personal"),
            TaskUI("Essay draft", "Due Friday", done = false, tag = "urgent")
        )
    }
    val filtered = when (selectedTab) {
        1 -> tasks.filter { !it.done }
        2 -> tasks.filter { it.done }
        else -> tasks
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showAdd = true }) { Text("+") }
        }
    ) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("My Tasks ✅", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(12.dp))
            SegmentedTabs(listOf("All", "Active", "Done"), selectedTab) { selectedTab = it }
            Spacer(Modifier.height(8.dp))

            LazyColumn(contentPadding = PaddingValues(bottom = 96.dp)) {
                items(filtered) { t -> TaskCard(t, onChecked = { t.done = it }) }
            }
        }
    }

    if (showAdd) AddTaskSheet(
        onDismiss = { showAdd = false },
        onSave = { tasks.add(0, it); showAdd = false }
    )
}

/* ----- UI models ----- */
data class TaskUI(
    val title: String,
    val subtitle: String = "",
    var done: Boolean = false,
    val tag: String? = null
)

/* ----- Card ----- */
@Composable
private fun TaskCard(t: TaskUI, onChecked: (Boolean) -> Unit) {
    Card(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row {
                    Checkbox(checked = t.done, onCheckedChange = onChecked)
                    Spacer(Modifier.width(6.dp))
                    Text(t.title, style = MaterialTheme.typography.titleMedium)
                }
                t.tag?.let { TagChip(it) }
            }
            if (t.subtitle.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(t.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

/* ----- Bottom sheet to add a task (UI only) ----- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTaskSheet(onDismiss: () -> Unit, onSave: (TaskUI) -> Unit) {
    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }
    var tag by remember { mutableStateOf("study") }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(16.dp)) {
            Text("Add Task", style = MaterialTheme.typography.titleLarge)
            TextFieldFilled(title, { title = it }, "Task title", singleLine = true)
            TextFieldFilled(subtitle, { subtitle = it }, "Optional details…")

            Spacer(Modifier.height(12.dp))
            Text("Tag", style = MaterialTheme.typography.labelLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("study", "personal", "urgent").forEach {
                    FilterChip(
                        selected = tag == it,
                        onClick = { tag = it },
                        label = { Text(it) }
                    )
                    Spacer(Modifier.width(4.dp))
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) { Text("Cancel") }
                Button(
                    onClick = { onSave(TaskUI(title.ifBlank { "Untitled" }, subtitle, false, tag)) },
                    enabled = title.isNotBlank(),
                    modifier = Modifier.weight(1f)
                ) { Text("Save") }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
