package com.keagan.studentplanner.ui.theme.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun ProgressRing(
    progress: Float,                  // 0f..1f
    ringSize: Dp = 160.dp,            // <-- renamed
    stroke: Dp = 14.dp,
    label: String = "Progress",
    centerText: String = "${(progress * 100).toInt()}%"
) {
    val track = MaterialTheme.colorScheme.outlineVariant
    val fill = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier.size(ringSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.fillMaxSize()) {
            // use the Canvas' size (no name clash)
            val canvasSize = this.size
            val diameter = min(canvasSize.width, canvasSize.height)
            val topLeft = Offset(
                (canvasSize.width - diameter) / 2f,
                (canvasSize.height - diameter) / 2f
            )
            val arcSize = Size(diameter, diameter)

            // track
            drawArc(
                color = track,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round),
                topLeft = topLeft,
                size = arcSize
            )
            // progress
            drawArc(
                color = fill,
                startAngle = -90f,
                sweepAngle = progress.coerceIn(0f, 1f) * 360f,
                useCenter = false,
                style = Stroke(width = stroke.toPx(), cap = StrokeCap.Round),
                topLeft = topLeft,
                size = arcSize
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(centerText, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
            Text(label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
        }
    }
}
