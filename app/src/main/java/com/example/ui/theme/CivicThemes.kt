package com.example.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ThemeMode(val title: String, val description: String) {
    DARK("Dark Theme", "Deep obsidian navy with luminous neon accents"),
    LIGHT("Light Theme", "Clean, high-contrast daylight with crisp slate text"),
    SYSTEM("System Default", "Automatically adapts to Android device appearance")
}

object CivicThemeTokens {
    // Dark mode tokens
    val darkBackground = Color(0xFF070E22)
    val darkSurface = Color(0xFF0D1733)
    val darkSurfaceVariant = Color(0xFF132247)
    val darkBorder = Color(0x3338BDF8)
    val darkTextPrimary = Color(0xFFF8FAFC)
    val darkTextSecondary = Color(0xFF94A3B8)
    val darkTextMuted = Color(0xFF64748B)

    // Light mode tokens
    val lightBackground = Color(0xFFF1F5F9)
    val lightSurface = Color(0xFFFFFFFF)
    val lightSurfaceVariant = Color(0xFFE2E8F0)
    val lightBorder = Color(0xFFCBD5E1)
    val lightTextPrimary = Color(0xFF0F172A)
    val lightTextSecondary = Color(0xFF475569)
    val lightTextMuted = Color(0xFF94A3B8)
}

@Composable
fun civicBackground(isDark: Boolean): Color =
    if (isDark) CivicThemeTokens.darkBackground else CivicThemeTokens.lightBackground

@Composable
fun civicSurface(isDark: Boolean): Color =
    if (isDark) CivicThemeTokens.darkSurface else CivicThemeTokens.lightSurface

@Composable
fun civicSurfaceVariant(isDark: Boolean): Color =
    if (isDark) CivicThemeTokens.darkSurfaceVariant else CivicThemeTokens.lightSurfaceVariant

@Composable
fun civicBorder(isDark: Boolean): Color =
    if (isDark) CivicThemeTokens.darkBorder else CivicThemeTokens.lightBorder

@Composable
fun civicTextPrimary(isDark: Boolean): Color =
    if (isDark) CivicThemeTokens.darkTextPrimary else CivicThemeTokens.lightTextPrimary

@Composable
fun civicTextSecondary(isDark: Boolean): Color =
    if (isDark) CivicThemeTokens.darkTextSecondary else CivicThemeTokens.lightTextSecondary

enum class ThemePreset(
    val title: String,
    val description: String,
    val primaryColor: Color,
    val accentColor: Color,
    val darkBackgroundColor: Color
) {
    BLUE_OCEAN(
        title = "Blue Ocean",
        description = "Civic Pulse signature deep navy with royal blue and luminous cyan",
        primaryColor = Color(0xFF2563EB),
        accentColor = Color(0xFF06B6D4),
        darkBackgroundColor = Color(0xFF070E22)
    ),
    DARK_PREMIUM(
        title = "Dark Premium",
        description = "Deep obsidian stealth with brilliant electric sky accents",
        primaryColor = Color(0xFF0284C7),
        accentColor = Color(0xFF6366F1),
        darkBackgroundColor = Color(0xFF0B0F19)
    ),
    LIGHT_CLASSIC(
        title = "Light Classic",
        description = "Clean modern daylight palette with deep navy contrast",
        primaryColor = Color(0xFF1E40AF),
        accentColor = Color(0xFF0284C7),
        darkBackgroundColor = Color(0xFF070E22)
    ),
    PURPLE_ROYAL(
        title = "Purple Royal",
        description = "Majestic midnight violet with neon purple and vibrant fuchsia",
        primaryColor = Color(0xFF7C3AED),
        accentColor = Color(0xFFD946EF),
        darkBackgroundColor = Color(0xFF0F0C20)
    ),
    GREEN_NATURE(
        title = "Green Nature",
        description = "Eco civic palette with lush emerald and rejuvenating mint",
        primaryColor = Color(0xFF059669),
        accentColor = Color(0xFF10B981),
        darkBackgroundColor = Color(0xFF061A14)
    ),
    SUNSET_GLOW(
        title = "Sunset Glow",
        description = "Dusk twilight gradient with radiant coral and warm amber",
        primaryColor = Color(0xFFEA580C),
        accentColor = Color(0xFFF43F5E),
        darkBackgroundColor = Color(0xFF1A0F1A)
    ),
    ROYAL_GOLD(
        title = "Royal Gold",
        description = "High-contrast luxury dark with gilded bronze and golden highlights",
        primaryColor = Color(0xFFD97706),
        accentColor = Color(0xFFF59E0B),
        darkBackgroundColor = Color(0xFF121212)
    ),
    OCEAN_BREEZE(
        title = "Ocean Breeze",
        description = "Deep coastal marine tones with turquoise and seafoam",
        primaryColor = Color(0xFF0D9488),
        accentColor = Color(0xFF06B6D4),
        darkBackgroundColor = Color(0xFF041D26)
    ),
    NEON_TECH(
        title = "Neon Tech",
        description = "Futuristic cyber civic matrix with electric cyan and neon lime",
        primaryColor = Color(0xFF0284C7),
        accentColor = Color(0xFF10B981),
        darkBackgroundColor = Color(0xFF050811)
    ),
    MINIMAL_ZEN(
        title = "Minimal Zen",
        description = "Minimalist slate grey and pure monochromatic elegance",
        primaryColor = Color(0xFF475569),
        accentColor = Color(0xFF0284C7),
        darkBackgroundColor = Color(0xFF0F172A)
    );

    val backgroundColor: Color get() = darkBackgroundColor

    fun toColorScheme(isDark: Boolean): ColorScheme {
        return if (!isDark) {
            lightColorScheme(
                primary = primaryColor,
                onPrimary = Color.White,
                primaryContainer = primaryColor.copy(alpha = 0.12f),
                onPrimaryContainer = primaryColor,
                secondary = accentColor,
                onSecondary = Color.White,
                secondaryContainer = accentColor.copy(alpha = 0.15f),
                onSecondaryContainer = accentColor,
                background = Color(0xFFF1F5F9),
                onBackground = Color(0xFF0F172A),
                surface = Color.White,
                onSurface = Color(0xFF0F172A),
                surfaceVariant = Color(0xFFE2E8F0),
                onSurfaceVariant = Color(0xFF475569),
                outline = Color(0xFFCBD5E1)
            )
        } else {
            darkColorScheme(
                primary = primaryColor,
                onPrimary = Color.White,
                primaryContainer = primaryColor.copy(alpha = 0.25f),
                onPrimaryContainer = Color.White,
                secondary = accentColor,
                onSecondary = Color.White,
                secondaryContainer = accentColor.copy(alpha = 0.20f),
                onSecondaryContainer = Color.White,
                background = darkBackgroundColor,
                onBackground = Color(0xFFF8FAFC),
                surface = Color(0xFF0D1733),
                onSurface = Color(0xFFF8FAFC),
                surfaceVariant = Color(0xFF132247),
                onSurfaceVariant = Color(0xFF94A3B8),
                outline = Color(0x3338BDF8)
            )
        }
    }
}
