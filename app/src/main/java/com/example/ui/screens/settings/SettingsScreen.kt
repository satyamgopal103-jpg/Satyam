package com.example.ui.screens.settings

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.User
import com.example.ui.components.CivicGlassCard
import com.example.ui.components.CivicPulseLogo
import com.example.ui.components.CivicSecondaryButton
import com.example.ui.theme.*
import com.example.viewmodel.Screen

@Composable
fun SettingsScreen(
    user: User,
    isDarkMode: Boolean,
    themeMode: ThemeMode = ThemeMode.DARK,
    currentThemePreset: ThemePreset = ThemePreset.BLUE_OCEAN,
    currentBackgroundPreset: BackgroundPreset = BackgroundPreset.ANIMATED_SKYLINE,
    currentFontPreset: FontPreset = FontPreset.DEFAULT_SANS,
    isNotificationsAllowed: Boolean = true,
    isLocationServicesAllowed: Boolean = true,
    isHapticFeedbackEnabled: Boolean = true,
    isHighContrastEnabled: Boolean = false,
    isAutoSyncEnabled: Boolean = true,
    preferredLanguage: String = "English",
    onToggleDarkMode: (Boolean) -> Unit,
    onSelectThemeMode: (ThemeMode) -> Unit = {},
    onSelectThemePreset: (ThemePreset) -> Unit = {},
    onToggleNotifications: (Boolean) -> Unit = {},
    onToggleLocationServices: (Boolean) -> Unit = {},
    onToggleHapticFeedback: (Boolean) -> Unit = {},
    onToggleHighContrast: (Boolean) -> Unit = {},
    onToggleAutoSync: (Boolean) -> Unit = {},
    onSelectLanguage: (String) -> Unit = {},
    onResetPreferences: () -> Unit = {},
    onClearCache: () -> Unit = {},
    onNavigate: (Screen) -> Unit,
    onLogout: () -> Unit,
    onBack: () -> Unit
) {
    var showResetDialog by remember { mutableStateOf(false) }

    val screenBg = if (isDarkMode) Color(0xFF070E22) else Color(0xFFF1F5F9)
    val cardBg = if (isDarkMode) Color(0xFF0D1733) else Color.White
    val cardBorder = if (isDarkMode) Color(0x3338BDF8) else Color(0xFFCBD5E1)
    val textPrimary = if (isDarkMode) Color.White else Color(0xFF0F172A)
    val textSecondary = if (isDarkMode) Color(0xFF94A3B8) else Color(0xFF475569)
    val sectionHeaderColor = if (isDarkMode) CivicCyan else Color(0xFF0284C7)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBg)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // 1. HUD Top Bar Header
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier.testTag("settings_back_btn")
                        ) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = sectionHeaderColor
                            )
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "SETTINGS & PREFERENCES",
                            color = textPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                    }

                    // Direct HUD Theme quick-toggle
                    IconButton(
                        onClick = { onToggleDarkMode(!isDarkMode) },
                        modifier = Modifier.testTag("settings_quick_theme_toggle")
                    ) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Appearance",
                            tint = if (isDarkMode) Color(0xFFFBBF24) else Color(0xFF1E40AF),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            // 2. Citizen Account Card
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = cardBg,
                    border = BorderStroke(1.dp, cardBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(Screen.Profile) }
                        .testTag("settings_view_profile")
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(CivicRoyalBlue)
                                .border(2.dp, CivicCyan, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.name.firstOrNull()?.toString() ?: "U",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = user.name,
                                    color = textPrimary,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    Icons.Default.Verified,
                                    contentDescription = "Verified Citizen",
                                    tint = CivicCyan,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Text(user.email, color = textSecondary, fontSize = 12.sp)
                            Text("Ward: ${user.ward} • ID: ${user.id}", color = sectionHeaderColor, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                        }

                        Icon(
                            Icons.Default.ChevronRight,
                            contentDescription = "View Profile",
                            tint = textSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // 3. Theme & Appearance Section (ViewModel + DataStore based)
            item {
                SectionHeader(title = "APPEARANCE & THEME PREFERENCES", color = sectionHeaderColor)
                Spacer(modifier = Modifier.height(10.dp))

                // 3-Option Visual Mode Cards: Dark / Light / System
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ThemeModeCard(
                        title = "Dark",
                        icon = Icons.Default.DarkMode,
                        isSelected = isDarkMode,
                        isDark = isDarkMode,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onToggleDarkMode(true)
                            onSelectThemeMode(ThemeMode.DARK)
                        },
                        testTag = "theme_mode_dark_card"
                    )

                    ThemeModeCard(
                        title = "Light",
                        icon = Icons.Default.LightMode,
                        isSelected = !isDarkMode,
                        isDark = isDarkMode,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onToggleDarkMode(false)
                            onSelectThemeMode(ThemeMode.LIGHT)
                        },
                        testTag = "theme_mode_light_card"
                    )

                    ThemeModeCard(
                        title = "System",
                        icon = Icons.Default.BrightnessAuto,
                        isSelected = themeMode == ThemeMode.SYSTEM,
                        isDark = isDarkMode,
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onSelectThemeMode(ThemeMode.SYSTEM)
                        },
                        testTag = "theme_mode_system_card"
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Toggle Switch for Dark Mode
                SettingsToggleRow(
                    title = "Dark Theme Mode",
                    subtitle = "Toggle deep obsidian city theme vs crisp daylight",
                    icon = Icons.Default.Nightlight,
                    checked = isDarkMode,
                    isDark = isDarkMode,
                    onCheckedChange = onToggleDarkMode,
                    testTag = "settings_dark_mode_switch"
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Quick Color Preset Carousel
                Text(
                    text = "ACTIVE PALETTE: ${currentThemePreset.title.uppercase()}",
                    color = textSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(ThemePreset.values()) { preset ->
                        val isSelected = preset == currentThemePreset
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) {
                                if (isDarkMode) Color(0xFF132247) else Color(0xFFE2E8F0)
                            } else cardBg,
                            border = BorderStroke(
                                if (isSelected) 2.dp else 1.dp,
                                if (isSelected) preset.primaryColor else cardBorder
                            ),
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { onSelectThemePreset(preset) }
                                .testTag("palette_chip_${preset.name}")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(preset.primaryColor)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(preset.accentColor)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = preset.title,
                                    color = textPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Deep Theme & Visual Studio Banner Link
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isDarkMode) Color(0xFF0F1E40) else Color(0xFFE0F2FE),
                    border = BorderStroke(1.5.dp, CivicCyan),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(Screen.Themes) }
                        .testTag("settings_theme_studio_entry")
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(CivicRoyalBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Palette,
                                    contentDescription = null,
                                    tint = CivicCyan,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "Theme & Visual Studio",
                                    color = textPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Custom Backgrounds • Fonts • UI Elements",
                                    color = sectionHeaderColor,
                                    fontSize = 11.5.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "Font: ${currentFontPreset.title} • Bg: ${currentBackgroundPreset.title}",
                                    color = textSecondary,
                                    fontSize = 10.5.sp
                                )
                            }
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = "Open Studio", tint = sectionHeaderColor)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // 4. HUD & Interface Experience Controls
            item {
                SectionHeader(title = "HUD & INTERFACE CONTROLS", color = sectionHeaderColor)
                Spacer(modifier = Modifier.height(10.dp))

                SettingsToggleRow(
                    title = "Haptic Touch & Sound Feedback",
                    subtitle = "Vibrate on button taps, voting, and status changes",
                    icon = Icons.Default.Vibration,
                    checked = isHapticFeedbackEnabled,
                    isDark = isDarkMode,
                    onCheckedChange = onToggleHapticFeedback,
                    testTag = "settings_haptic_switch"
                )

                Spacer(modifier = Modifier.height(8.dp))

                SettingsToggleRow(
                    title = "High Contrast Mode",
                    subtitle = "Enhance borders and card readability for accessibility",
                    icon = Icons.Default.Contrast,
                    checked = isHighContrastEnabled,
                    isDark = isDarkMode,
                    onCheckedChange = onToggleHighContrast,
                    testTag = "settings_contrast_switch"
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            // 5. Civic Governance & Permissions
            item {
                SectionHeader(title = "GOVERNANCE & PERMISSIONS", color = sectionHeaderColor)
                Spacer(modifier = Modifier.height(10.dp))

                SettingsToggleRow(
                    title = "Push Notifications & Alerts",
                    subtitle = "Real-time updates on reported issues and civic polls",
                    icon = Icons.Default.NotificationsActive,
                    checked = isNotificationsAllowed,
                    isDark = isDarkMode,
                    onCheckedChange = onToggleNotifications,
                    testTag = "settings_notifications_switch"
                )

                Spacer(modifier = Modifier.height(8.dp))

                SettingsToggleRow(
                    title = "GPS Geo-Location Tagging",
                    subtitle = "Auto-attach coordinates when filing civic grievances",
                    icon = Icons.Default.LocationOn,
                    checked = isLocationServicesAllowed,
                    isDark = isDarkMode,
                    onCheckedChange = onToggleLocationServices,
                    testTag = "settings_location_switch"
                )

                Spacer(modifier = Modifier.height(8.dp))

                SettingsToggleRow(
                    title = "Real-Time Cloud Auto-Sync",
                    subtitle = "Keep neighborhood projects and poll counts in sync",
                    icon = Icons.Default.CloudSync,
                    checked = isAutoSyncEnabled,
                    isDark = isDarkMode,
                    onCheckedChange = onToggleAutoSync,
                    testTag = "settings_autosync_switch"
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            // 6. Language & Localization
            item {
                SectionHeader(title = "REGIONAL & LANGUAGE", color = sectionHeaderColor)
                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = cardBg,
                    border = BorderStroke(1.dp, cardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Language, contentDescription = null, tint = sectionHeaderColor, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("Civic App Language", color = textPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                Text("Current: $preferredLanguage", color = textSecondary, fontSize = 11.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("English", "हिन्दी", "Español", "Français").forEach { lang ->
                                val selected = lang == preferredLanguage || (lang == "English" && preferredLanguage == "English")
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (selected) CivicRoyalBlue else (if (isDarkMode) Color(0xFF132247) else Color(0xFFF1F5F9)),
                                    border = BorderStroke(1.dp, if (selected) CivicCyan else cardBorder),
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { onSelectLanguage(lang) }
                                        .testTag("lang_chip_$lang")
                                ) {
                                    Box(modifier = Modifier.padding(vertical = 8.dp), contentAlignment = Alignment.Center) {
                                        Text(
                                            text = lang,
                                            color = if (selected) Color.White else textPrimary,
                                            fontSize = 11.sp,
                                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // 7. Data & Storage Management
            item {
                SectionHeader(title = "DATA & STORAGE", color = sectionHeaderColor)
                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = cardBg,
                    border = BorderStroke(1.dp, cardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Offline Maps & Image Cache", color = textPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                Text("24.8 MB cached on device", color = textSecondary, fontSize = 11.sp)
                            }
                            OutlinedButton(
                                onClick = onClearCache,
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, sectionHeaderColor),
                                modifier = Modifier.testTag("settings_clear_cache_btn")
                            ) {
                                Text("Clear", color = sectionHeaderColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        HorizontalDivider(color = cardBorder, modifier = Modifier.padding(vertical = 12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Reset Preferences", color = textPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                Text("Restore default theme and notification settings", color = textSecondary, fontSize = 11.sp)
                            }
                            OutlinedButton(
                                onClick = { showResetDialog = true },
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, Color(0xFFEF4444)),
                                modifier = Modifier.testTag("settings_reset_prefs_btn")
                            ) {
                                Text("Reset", color = Color(0xFFEF4444), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }

            // 8. Platform Info & About
            item {
                SectionHeader(title = "ABOUT CIVIC PULSE", color = sectionHeaderColor)
                Spacer(modifier = Modifier.height(10.dp))

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = cardBg,
                    border = BorderStroke(1.dp, cardBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CivicPulseLogo(size = 32.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text("CIVIC PULSE v1.2.0", color = textPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Text("Your Community. Your Voice. Your Future.", color = sectionHeaderColor, fontSize = 11.sp)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Transparent civic governance connecting residents directly with local council representatives and municipal project tracking.",
                            color = textSecondary,
                            fontSize = 11.5.sp,
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Sign Out Button
                CivicSecondaryButton(
                    text = "Sign Out Resident Account",
                    testTag = "settings_logout_btn",
                    onClick = onLogout
                )

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }

    // Confirmation dialog for Reset
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset All Preferences?", fontWeight = FontWeight.Bold) },
            text = { Text("This will restore the default Dark Theme, clear your customized typography and background settings, and re-enable default notifications.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showResetDialog = false
                        onResetPreferences()
                    }
                ) {
                    Text("Confirm Reset", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun SectionHeader(title: String, color: Color) {
    Text(
        text = title,
        color = color,
        fontSize = 11.5.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp
    )
}

@Composable
private fun ThemeModeCard(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    isDark: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    testTag: String
) {
    val cardBg = if (isSelected) {
        if (isDark) Color(0xFF132247) else Color(0xFFE0F2FE)
    } else {
        if (isDark) Color(0xFF0D1733) else Color.White
    }
    val borderColor = if (isSelected) CivicCyan else (if (isDark) Color(0x3338BDF8) else Color(0xFFCBD5E1))
    val textColor = if (isDark) Color.White else Color(0xFF0F172A)

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = cardBg,
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor),
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) CivicCyan else (if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)),
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                color = textColor,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
            if (isSelected) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "ACTIVE",
                    color = CivicCyan,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

@Composable
private fun SettingsToggleRow(
    title: String,
    subtitle: String,
    icon: ImageVector,
    checked: Boolean,
    isDark: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    testTag: String
) {
    val cardBg = if (isDark) Color(0xFF0D1733) else Color.White
    val cardBorder = if (isDark) Color(0x3338BDF8) else Color(0xFFCBD5E1)
    val textPrimary = if (isDark) Color.White else Color(0xFF0F172A)
    val textSecondary = if (isDark) Color(0xFF94A3B8) else Color(0xFF64748B)

    Surface(
        shape = RoundedCornerShape(14.dp),
        color = cardBg,
        border = BorderStroke(1.dp, cardBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f).padding(end = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(if (isDark) Color(0xFF132247) else Color(0xFFF1F5F9)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = CivicCyan, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(title, color = textPrimary, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Text(subtitle, color = textSecondary, fontSize = 11.sp, lineHeight = 15.sp)
                }
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.testTag(testTag)
            )
        }
    }
}
