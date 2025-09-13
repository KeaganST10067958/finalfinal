package com.keagan.studentplanner.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors: ColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = OnSurface,

    surface = SurfaceSoft,
    onSurface = OnSurface,

    background = SurfaceSoft,
    onBackground = OnSurface,

    outline = TextGrey,
    outlineVariant = Color(0xFFE6E1EA)
)

@Composable
fun PlannerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // light UI regardless; keep param if you add dark later
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = AppTypography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
