package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.keagan.studentplanner.model.NoteUI


@Composable
fun NotesScreen() {
    var selectedTab by remember { mutableIntStateOf(0) } // 0=All, 1=Pinned
    var showAdd by remember { mutableStateOf(false) }

    // demo data
    val notes = remember {
        mutableStateListOf(
            NoteUI("Exam dates", "• 12 Sep – CS\n• 18 Sep – Math", Color(0xFFD8F3DC), pinned = true, meta = "Pinned • 22 Aug")
        )
    }
    val visible = if (selectedTab == 1) notes.filter { it.pinned } else notes

    Scaffold(
        floatingActionButton = { FloatingActionButton(onClick = { showAdd = true }) { Text("+") } }
    ) { inner ->
        Column(Modifier.padding(inner).padding(16.dp)) {
            Text("Notes", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(10.dp))

            // search (no logic yet — UI only)
            var query by remember { mutableStateOf("") }
            OutlinedTextField(
                value = query, onValueChange = { query = it },
                placeholder = { Text("Search notes…") }, singleLine = true, modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))
            SegmentedTabs(tabs = listOf("All", "Pinned"), selected = selectedTab) { selectedTab = it }
            Spacer(Modifier.height(8.dp))

            LazyColumn(contentPadding = PaddingValues(bottom = 96.dp)) {
                items(visible) { note -> NoteCard(note) }
            }
        }
    }

    if (showAdd) {
        AddNoteSheet(
            onDismiss = { showAdd = false },
            onSave = { n -> notes.add(0, n); showAdd = false }
        )
    }
}

/* ----- UI models ----- */
data class NoteUI(
    val title: String,
    val body: String,
    val tint: Color,
    val pinned: Boolean = false,
    val meta: String = ""
)

/* ----- Card ----- */
@Composable
private fun NoteCard(n: NoteUI) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = n.tint.copy(alpha = 0.4f))
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(n.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Text(n.body)
            if (n.meta.isNotBlank()) {
                Spacer(Modifier.height(6.dp))
                Text(n.meta, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
}

/* ----- Bottom sheet to add a note (UI only) ----- */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddNoteSheet(onDismiss: () -> Unit, onSave: (NoteUI) -> Unit) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var chosen by remember { mutableIntStateOf(0) }
    val swatches = listOf(
        Color(0xFFF9BFBF), // Peach
        Color(0xFFD8F3DC), // Mint
        Color(0xFFA7D8F5), // Blue
        Color(0xFFE6E6FA), // Lavender
        Color(0xFFFFF9C4)  // Lemon
    )

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(Modifier.padding(16.dp)) {
            Text("Add Note", style = MaterialTheme.typography.titleLarge)
            TextFieldFilled(title, { title = it }, "e.g. Exam plan", singleLine = true)
            TextFieldFilled(body, { body = it }, "Type your note…")

            Spacer(Modifier.height(12.dp))
            Text("Colour", style = MaterialTheme.typography.labelLarge)
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                swatches.forEachIndexed { i, c ->
                    val selected = i == chosen
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(MaterialTheme.shapes.large)
                            .background(c)
                            .border(
                                width = if (selected) 2.dp else 0.dp,
                                color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                shape = MaterialTheme.shapes.large
                            )
                            .padding(0.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                }
            }

            Spacer(Modifier.height(12.dp))
            Text("Preview", style = MaterialTheme.typography.labelLarge)
            Card(colors = CardDefaults.cardColors(containerColor = swatches[chosen].copy(alpha = 0.4f))) {
                Column(Modifier.padding(16.dp)) {
                    Text(if (title.isBlank()) "Untitled" else title, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(4.dp))
                    Text(if (body.isBlank()) "Type your note…" else body, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) { Text("Cancel") }
                Button(
                    onClick = { onSave(NoteUI(title.ifBlank { "Untitled" }, body, swatches[chosen])) },
                    enabled = body.isNotBlank(),
                    modifier = Modifier.weight(1f)
                ) { Text("Save") }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}
