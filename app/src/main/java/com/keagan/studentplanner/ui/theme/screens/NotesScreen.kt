@file:OptIn(ExperimentalMaterial3Api::class)

package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.keagan.studentplanner.model.NoteUI
import com.keagan.studentplanner.ui.theme.*  // for TextGrey, PeachLight, etc.
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.PushPin

/* ---------- Public screen your NavHost calls ---------- */
@Composable
fun NotesScreen() {
    // simple in-memory list so it runs immediately
    var notes by rememberSaveable {
        mutableStateOf(
            listOf(
                NoteUI("Exam dates", "• 12 Sep — CS\n• 18 Sep — Math", tint = MintLight, pinned = true,  meta = "Pinned • 22 Aug"),
                NoteUI("Groceries",  "Snacks for study group",           tint = LemonLight,       pinned = false, meta = ""),
                NoteUI("Ideas",      "Dashboard quick actions",           tint = LavenderLight,    pinned = false, meta = "")
            )
        )
    }

    // very basic list UI; you can add search/tabs later
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(notes) { n ->
            NoteCard(
                note = n,
                onPin = {
                    notes = notes.map { if (it === n) it.copy(pinned = !it.pinned) else it }
                },
                onDelete = {
                    notes = notes.filterNot { it === n }
                }
            )
        }
    }
}

/* ---------- Card used by the screen ---------- */
@Composable
private fun NoteCard(note: NoteUI, onPin: () -> Unit, onDelete: () -> Unit) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(containerColor = note.tint.copy(alpha = 0.6f))
    ) {
        Column(Modifier.padding(14.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    note.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onPin) {
                    Icon(
                        imageVector = Icons.Outlined.PushPin,
                        contentDescription = if (note.pinned) "Unpin" else "Pin",
                        tint = if (note.pinned) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete")
                }
            }
            if (note.body.isNotBlank()) {
                Spacer(Modifier.height(4.dp))
                Text(note.body, style = MaterialTheme.typography.bodyMedium)
            }
            if (note.meta.isNotBlank()) {
                Spacer(Modifier.height(6.dp))
                Text(note.meta, style = MaterialTheme.typography.bodySmall, color = TextGrey)
            }
        }
    }
}
