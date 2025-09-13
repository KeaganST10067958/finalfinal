package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun SegmentedTabs(
    tabs: List<String>,
    selected: Int,
    onSelect: (Int) -> Unit
) {
    SingleChoiceSegmentedButtonRow {
        tabs.forEachIndexed { index, label ->
            SegmentedButton(
                selected = selected == index,
                onClick = { onSelect(index) },
                shape = SegmentedButtonDefaults.itemShape(index, tabs.lastIndex),
                label = { Text(label) }
            )
        }
    }
}
