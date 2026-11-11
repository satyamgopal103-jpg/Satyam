package com.example.ui.screens.themes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ComplaintStatus
import com.example.ui.components.*
import com.example.ui.theme.*

enum class ThemeStudioTab(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    PALETTES("Colors", Icons.Default.Palette),
    BACKGROUNDS("Backgrounds", Icons.Default.Wallpaper),
    FONTS("Typography", Icons.Default.FontDownload),
    UI_ELEMENTS("UI Elements", Icons.Default.Widgets)
}

@Composable
fun ThemesScreen(
    currentTheme: ThemePreset,
    currentBackground: BackgroundPreset,
    currentFont: FontPreset,
    isDarkMode: Boolean,
    onSelectTheme: (ThemePreset) -> Unit,
    onSelectBackground: (BackgroundPreset) -> Unit,
    onSelectFont: (FontPreset) -> Unit,
    onToggleDarkMode: (Boolean) -> Unit,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(ThemeStudioTab.PALETTES) }

    // Playground Interactive UI Elements State
    var sliderValue by remember { mutableFloatStateOf(0.74f) }
    var toggleStateA by remember { mutableStateOf(true) }
    var toggleStateB by remember { mutableStateOf(false) }
    var demoInputText by remember { mutableStateOf("Metro North Civic Council") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070E22))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CivicCyan)
                }
                Text(
                    text = "THEME & DESIGN STUDIO",
                    color = CivicWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            // Top Studio Tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFF0D1733))
                    .border(1.dp, Color(0x3338BDF8), RoundedCornerShape(14.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ThemeStudioTab.values().forEach { tab ->
                    val isSelected = selectedTab == tab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) CivicRoyalBlue else Color.Transparent)
                            .clickable { selectedTab = tab }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                                tint = if (isSelected) Color.White else Color(0xFF94A3B8),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = tab.title,
                                color = if (isSelected) Color.White else Color(0xFF94A3B8),
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Tab Content
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
            ) {
                // 1. Color Palettes Tab
                if (selectedTab == ThemeStudioTab.PALETTES) {
                    item {
                        // Dark Mode Toggle
                        CivicGlassCard(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("Dark Theme", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    Text("Deep navy background with high-contrast text", color = Color(0xFF94A3B8), fontSize = 11.sp)
                                }
                                Switch(
                                    checked = isDarkMode,
                                    onCheckedChange = { onToggleDarkMode(it) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "10 SELECTABLE COLOR PALETTES",
                            color = Color(0xFF94A3B8),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(ThemePreset.values()) { theme ->
                        val isSelected = currentTheme == theme
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clickable { onSelectTheme(theme) }
                                .testTag("theme_preset_${theme.name}"),
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFF0D1733),
                            border = BorderStroke(1.5.dp, if (isSelected) CivicCyan else Color(0x3338BDF8))
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Tri-color palette preview
                                Row(horizontalArrangement = Arrangement.spacedBy((-6).dp)) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(theme.primaryColor)
                                            .border(1.5.dp, Color.White, CircleShape)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(theme.accentColor)
                                            .border(1.5.dp, Color.White, CircleShape)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clip(CircleShape)
                                            .background(theme.backgroundColor)
                                            .border(1.5.dp, Color.White, CircleShape)
                                    )
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = theme.title,
                                        color = if (isSelected) CivicCyan else Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = theme.description,
                                        color = Color(0xFF94A3B8),
                                        fontSize = 11.sp,
                                        lineHeight = 15.sp
                                    )
                                }

                                if (isSelected) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = "Active",
                                        tint = CivicCyan,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // 2. Backgrounds Tab
                if (selectedTab == ThemeStudioTab.BACKGROUNDS) {
                    item {
                        Text(
                            text = "5 DYNAMIC BACKGROUND STYLES",
                            color = Color(0xFF94A3B8),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    items(BackgroundPreset.values()) { bg ->
                        val isSelected = currentBackground == bg
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable { onSelectBackground(bg) }
                                .testTag("background_preset_${bg.name}"),
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFF0D1733),
                            border = BorderStroke(1.5.dp, if (isSelected) CivicCyan else Color(0x3338BDF8))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(bg.iconEmoji, fontSize = 24.sp)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = bg.title,
                                                color = if (isSelected) CivicCyan else Color.White,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = bg.description,
                                                color = Color(0xFF94A3B8),
                                                fontSize = 11.sp
                                            )
                                        }
                                    }

                                    if (isSelected) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = "Selected",
                                            tint = CivicCyan,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Live Mini-Canvas Preview
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(70.dp)
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(1.dp, Color(0x3338BDF8), RoundedCornerShape(10.dp))
                                ) {
                                    CivicDynamicBackground(backgroundPreset = bg) {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "Preview: ${bg.title}",
                                                color = Color.White.copy(alpha = 0.85f),
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // 3. Fonts / Typography Tab
                if (selectedTab == ThemeStudioTab.FONTS) {
                    item {
                        Text(
                            text = "4 DISTINCT CIVIC FONTS & TYPOGRAPHY",
                            color = Color(0xFF94A3B8),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Downloaded locally from Google Fonts for offline performance.",
                            color = Color(0xFF64748B),
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    items(FontPreset.values()) { font ->
                        val isSelected = currentFont == font
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable { onSelectFont(font) }
                                .testTag("font_preset_${font.name}"),
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFF0D1733),
                            border = BorderStroke(1.5.dp, if (isSelected) CivicCyan else Color(0x3338BDF8))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text(
                                            text = font.title,
                                            color = if (isSelected) CivicCyan else Color.White,
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = font.description,
                                            color = Color(0xFF94A3B8),
                                            fontSize = 11.sp
                                        )
                                    }

                                    if (isSelected) {
                                        Icon(
                                            Icons.Default.CheckCircle,
                                            contentDescription = "Selected",
                                            tint = CivicCyan,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                // Font Type Specimen Box
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = Color(0xFF070E22),
                                    border = BorderStroke(1.dp, Color(0x2238BDF8)),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = font.sampleText,
                                            fontFamily = font.toFontFamily(),
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "Every voice matters. Every community counts. Together for a Better Tomorrow.",
                                            fontFamily = font.toFontFamily(),
                                            color = Color(0xFFCBD5E1),
                                            fontSize = 12.sp,
                                            lineHeight = 17.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // 4. UI Elements & Design System Showcase Tab
                if (selectedTab == ThemeStudioTab.UI_ELEMENTS) {
                    item {
                        Text(
                            text = "LIVING UI ELEMENTS PLAYGROUND",
                            color = Color(0xFF94A3B8),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Real-time preview of all primary buttons, chips, badges, switches, and cards under the active theme.",
                            color = Color(0xFFCBD5E1),
                            fontSize = 12.sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // A. Buttons Showcase
                        CivicGlassCard(modifier = Modifier.fillMaxWidth()) {
                            Text("1. BUTTONS & ACTIONS", color = CivicCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.height(10.dp))

                            CivicButton(
                                text = "PRIMARY GRADIENT BUTTON",
                                onClick = {},
                                leadingIcon = { Icon(Icons.Default.FlashOn, contentDescription = null, tint = Color.White) }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            CivicSecondaryButton(
                                text = "SECONDARY OUTLINED BUTTON",
                                onClick = {},
                                leadingIcon = { Icon(Icons.Default.Explore, contentDescription = null, tint = CivicCyan) }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            CivicButton(
                                text = "LOADING STATE BUTTON",
                                isLoading = true,
                                onClick = {}
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // B. Status Pills & Badges
                        CivicGlassCard(modifier = Modifier.fillMaxWidth()) {
                            Text("2. GRIEVANCE STATUS BADGES", color = CivicCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                CivicStatusBadge(status = ComplaintStatus.SUBMITTED)
                                CivicStatusBadge(status = ComplaintStatus.UNDER_REVIEW)
                                CivicStatusBadge(status = ComplaintStatus.IN_PROGRESS)
                                CivicStatusBadge(status = ComplaintStatus.RESOLVED)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // C. Interactive Sliders, Progress & Controls
                        CivicGlassCard(modifier = Modifier.fillMaxWidth()) {
                            Text("3. CONTROLS & PROGRESS", color = CivicCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Budget Allocation Slider", color = Color.White, fontSize = 13.sp)
                                Text("${(sliderValue * 100).toInt()}%", color = CivicCyan, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }

                            Slider(
                                value = sliderValue,
                                onValueChange = { sliderValue = it },
                                colors = SliderDefaults.colors(
                                    thumbColor = CivicCyan,
                                    activeTrackColor = CivicRoyalBlue
                                )
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Auto-GPS Tagging", color = Color(0xFFCBD5E1), fontSize = 12.sp)
                                Switch(checked = toggleStateA, onCheckedChange = { toggleStateA = it })
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Live Push Notifications", color = Color(0xFFCBD5E1), fontSize = 12.sp)
                                Switch(checked = toggleStateB, onCheckedChange = { toggleStateB = it })
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // D. Input Fields Preview
                        CivicGlassCard(modifier = Modifier.fillMaxWidth()) {
                            Text("4. TEXT FIELDS & INPUTS", color = CivicCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = demoInputText,
                                onValueChange = { demoInputText = it },
                                label = { Text("Ward Name / Department", color = Color(0xFF94A3B8)) },
                                leadingIcon = { Icon(Icons.Default.LocationCity, contentDescription = null, tint = CivicCyan) },
                                singleLine = true,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = CivicCyan,
                                    unfocusedBorderColor = Color(0x6638BDF8),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}
