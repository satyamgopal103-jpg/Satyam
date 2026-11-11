package com.example.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.R

enum class FontPreset(
    val title: String,
    val description: String,
    val sampleText: String,
    val fontResId: Int?
) {
    DEFAULT_SANS(
        title = "Inter / Modern Sans",
        description = "Clean, highly legible everyday modern digital font",
        sampleText = "Civic Pulse — Modern Community",
        fontResId = null
    ),
    MONTSERRAT(
        title = "Montserrat",
        description = "Geometric, architectural, high-tech civic style",
        sampleText = "CIVIC PULSE — ARCHITECTURAL GEOMETRY",
        fontResId = R.font.montserrat
    ),
    PLAYFAIR(
        title = "Playfair Display",
        description = "Prestigious editorial, classical civic charter style",
        sampleText = "Civic Pulse — A Better Tomorrow",
        fontResId = R.font.playfair_display
    ),
    ROBOTO_MONO(
        title = "Roboto Mono",
        description = "Data-driven, precise monospace telemetry style",
        sampleText = "CIVIC_PULSE_SYS // STATUS: ACTIVE",
        fontResId = R.font.roboto_mono
    );

    fun toFontFamily(): FontFamily {
        return if (fontResId != null) {
            FontFamily(Font(fontResId, FontWeight.Normal))
        } else {
            FontFamily.Default
        }
    }

    fun toTypography(): Typography {
        val family = toFontFamily()
        return Typography(
            displayLarge = TextStyle(fontFamily = family, fontWeight = FontWeight.Bold, fontSize = 34.sp, letterSpacing = (-0.5).sp),
            displayMedium = TextStyle(fontFamily = family, fontWeight = FontWeight.Bold, fontSize = 28.sp),
            headlineLarge = TextStyle(fontFamily = family, fontWeight = FontWeight.Bold, fontSize = 24.sp),
            headlineMedium = TextStyle(fontFamily = family, fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
            titleLarge = TextStyle(fontFamily = family, fontWeight = FontWeight.Bold, fontSize = 18.sp),
            titleMedium = TextStyle(fontFamily = family, fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
            titleSmall = TextStyle(fontFamily = family, fontWeight = FontWeight.Medium, fontSize = 14.sp),
            bodyLarge = TextStyle(fontFamily = family, fontWeight = FontWeight.Normal, fontSize = 15.sp, lineHeight = 22.sp),
            bodyMedium = TextStyle(fontFamily = family, fontWeight = FontWeight.Normal, fontSize = 13.sp, lineHeight = 18.sp),
            bodySmall = TextStyle(fontFamily = family, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp),
            labelLarge = TextStyle(fontFamily = family, fontWeight = FontWeight.Bold, fontSize = 14.sp),
            labelMedium = TextStyle(fontFamily = family, fontWeight = FontWeight.SemiBold, fontSize = 12.sp),
            labelSmall = TextStyle(fontFamily = family, fontWeight = FontWeight.Medium, fontSize = 11.sp)
        )
    }
}
