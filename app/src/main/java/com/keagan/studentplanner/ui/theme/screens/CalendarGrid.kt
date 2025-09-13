package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CalendarMonth(
    month: YearMonth,
    today: LocalDate = LocalDate.now()
) {
    val daysOfWeek = listOf(
        DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY
    )

    // Header (Mon..Sun)
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        daysOfWeek.forEach {
            Box(Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Text(it.name.take(2), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
            }
        }
    }
    Spacer(Modifier.height(8.dp))

    val firstOfMonth = month.atDay(1)
    val startOffset = ((firstOfMonth.dayOfWeek.value + 6) % 7) // Monday=1 -> 0
    val totalDays = month.lengthOfMonth()

    Column(Modifier.fillMaxWidth()) {
        var dayNumber = 1
        repeat(6) { // 6 rows = 42 cells
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                repeat(7) { col ->
                    val number = (col - startOffset) + dayNumber
                    val inMonth = number in 1..totalDays
                    val date = if (inMonth) month.atDay(number) else null
                    if (inMonth) dayNumber++

                    DayCell(
                        day = number.takeIf { inMonth },
                        isToday = (date == today),
                        modifier = Modifier.weight(1f) // <— weight applied here (RowScope)
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
        }
    }
}

@Composable
private fun DayCell(day: Int?, isToday: Boolean, modifier: Modifier = Modifier) {
    val shape = MaterialTheme.shapes.large
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(shape)
            .background(
                if (isToday) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                else MaterialTheme.colorScheme.surface
            ),
        contentAlignment = Alignment.TopStart
    ) {
        if (day != null) {
            Text(
                day.toString(),
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = if (isToday) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
