package com.keagan.studentplanner.model

import androidx.compose.ui.graphics.Color
import com.keagan.studentplanner.ui.theme.PeachLight
import com.keagan.studentplanner.ui.theme.MintLight
import com.keagan.studentplanner.ui.theme.BlueLight
import com.keagan.studentplanner.ui.theme.LavenderLight
import com.keagan.studentplanner.ui.theme.LemonLight

data class NoteUI(
    val title: String,
    val body: String,
    val tint: Color = PeachLight,
    val pinned: Boolean = false,
    val meta: String = ""
)

data class TaskUI(
    val title: String,
    val subtitle: String = "",
    var done: Boolean = false,
    val tag: String? = null
)

val SubjectTint: Map<String, Color> = mapOf(
    "study" to MintLight,
    "personal" to LavenderLight,
    "urgent" to PeachLight,
    "math" to BlueLight,
    "cs" to MintLight,
    "default" to LemonLight
)
