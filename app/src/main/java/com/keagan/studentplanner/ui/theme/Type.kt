package com.keagan.studentplanner.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography = Typography(
    headlineSmall = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold),
    titleLarge    = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold),
    titleMedium   = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
    bodyMedium    = TextStyle(fontSize = 14.sp),
    bodySmall     = TextStyle(fontSize = 12.sp, color = TextGrey)
)
