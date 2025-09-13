@file:OptIn(ExperimentalMaterial3Api::class)

package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.keagan.studentplanner.model.TaskUI
import com.keagan.studentplanner.ui.theme.TextGrey
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.Date

@Composable
fun TodoScreen() {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showSheet by rememberSaveable { mutableStateOf(false) }

    var tab by rememberSaveable { mutableIntStateOf(0) } // 0=All 1=Active 2=Done
    var tasks by rememberSaveable {
        mutableStateOf(
            listOf(
                TaskUI("Revise Chemistry", "Flashcards 20min", done = false, tag = "study"),
                TaskUI("Buy snacks", "For study group", done = true, tag = "personal"),
                TaskUI("Essay draft", "Due Friday", done = false, tag = "urgent")
            )
        )
    }

    val filtered = tasks.filter {
        when (tab) {
            1 -> !it.done
            2 -> it.done
            else -> true
        }
    }

    Scaffold(topBar = { TopBar("My Tasks", actions = { AddAction { showSheet = true } }) }) { inner ->
        Column(
            Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            SegmentedTabs(tabs = listOf("All", "Active", "Done"), selected = tab, onSelect = { tab = it })
            Spacer(Modifier.height(12.dp))

            if (filtered.isEmpty()) {
                EmptyState(
                    title = "No tasks",
                    message = "Add your first task.",
                    actionLabel = "Add",
                    onAction = { showSheet = true }
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filtered, key = { it.title + it.subtitle }) { t ->
                        TaskRow(
                            task = t,
                            onToggle = { tasks = tasks.map { if (it === t) it.copy(done = !it.done) else it } }
                        )
                    }
                }
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(sheetState = sheetState, onDismissRequest = { showSheet = false }) {
            var title by rememberSaveable { mutableStateOf("") }
            var subtitle by rememberSaveable { mutableStateOf("") }
            var tag by rememberSaveable { mutableStateOf("study") }
            var dueMillis by rememberSaveable { mutableStateOf<Long?>(null) }

            val tags = listOf("study", "personal", "urgent")

            // Date picker state
            var showDatePicker by remember { mutableStateOf(false) }
            val dateState = rememberDatePickerState()

            Column(Modifier.padding(16.dp)) {
                Text("Add Task", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = title, onValueChange = { title = it },
                    placeholder = { Text("Task title") },
                    singleLine = true, modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = subtitle, onValueChange = { subtitle = it },
                    placeholder = { Text("Optional details") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))
                Text("Tag", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    tags.forEach { t ->
                        FilterChip(
                            selected = tag == t,
                            onClick = { tag = t },
                            label = { Text(t) }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))
                Text("Due date", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = { showDatePicker = true }) {
                        Text(if (dueMillis == null) "Pick date" else "Change")
                    }
                    Text(
                        dueMillis?.let { formatMediumDate(it) } ?: "No date",
                        color = MaterialTheme.colorScheme.outline
                    )
                }

                Spacer(Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = { showSheet = false }) { Text("Cancel") }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                tasks = listOf(TaskUI(title, subtitle, false, tag, dueMillis)) + tasks
                            }
                            scope.launch { sheetState.hide() }.invokeOnCompletion { showSheet = false }
                        }
                    ) { Text("Save") }
                }
                Spacer(Modifier.height(8.dp))
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = {
                            dueMillis = dateState.selectedDateMillis
                            showDatePicker = false
                        }) { Text("Set") }
                    },
                    dismissButton = { TextButton(onClick = { showDatePicker = false }) { Text("Cancel") } }
                ) { DatePicker(state = dateState) }
            }
        }
    }
}

@Composable
private fun TaskRow(task: TaskUI, onToggle: () -> Unit) {
    ElevatedCard {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = task.done, onCheckedChange = { onToggle() })
            Column(Modifier.weight(1f)) {
                Text(task.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                if (task.subtitle.isNotBlank())
                    Text(task.subtitle, style = MaterialTheme.typography.bodySmall, color = TextGrey)
                task.dueAtMillis?.let { millis ->
                    Text(
                        "Due ${formatMediumDate(millis)}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            task.tag?.takeIf { it.isNotBlank() }?.let { tagText ->
                AssistChip(onClick = {}, label = { Text(tagText) })
            }
        }
    }
}

/** simple helper instead of an extension to avoid resolution issues */
private fun formatMediumDate(millis: Long): String =
    DateFormat.getDateInstance(DateFormat.MEDIUM).format(Date(millis))
