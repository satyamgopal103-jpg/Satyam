package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite
import com.example.viewmodel.Screen

data class DrawerMenuItem(
    val title: String,
    val icon: ImageVector,
    val screen: Screen?,
    val isDestructive: Boolean = false,
    val isHighlighted: Boolean = false
)

@Composable
fun CivicDrawer(
    isOpen: Boolean,
    isDarkMode: Boolean = true,
    user: User? = null,
    onClose: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onToggleDarkMode: () -> Unit = {},
    onLogout: () -> Unit
) {
    val drawerBg = if (isDarkMode) Color(0xFF070E22) else Color(0xFFFFFFFF)
    val drawerBorder = if (isDarkMode) Color(0x3338BDF8) else Color(0xFFE2E8F0)
    val itemTextColor = if (isDarkMode) CivicWhite else Color(0xFF0F172A)
    val dividerColor = if (isDarkMode) Color(0x2238BDF8) else Color(0xFFE2E8F0)

    val menuItems = listOf(
        DrawerMenuItem("Home Dashboard", Icons.Default.Home, Screen.Home),
        DrawerMenuItem("Complaints & Grievances", Icons.Default.ReportProblem, Screen.Complaints),
        DrawerMenuItem("Find Services", Icons.Default.Search, Screen.Explore),
        DrawerMenuItem("Civic Maps", Icons.Default.Map, Screen.CivicMap),
        DrawerMenuItem("MLA & Leaders", Icons.Default.AccountBalance, Screen.Representatives),
        DrawerMenuItem("Projects & Initiatives", Icons.Default.Engineering, Screen.Projects),
        DrawerMenuItem("Communities", Icons.Default.Groups, Screen.Communities),
        DrawerMenuItem("Polls & Surveys", Icons.Default.HowToVote, Screen.Polls),
        DrawerMenuItem("Volunteering", Icons.Default.VolunteerActivism, Screen.Volunteering),
        DrawerMenuItem("Civic Assistant (AI)", Icons.Default.SmartToy, Screen.CivicAssistant, isHighlighted = true),
        DrawerMenuItem("Messages & Channels", Icons.Default.Forum, Screen.Messages),
        DrawerMenuItem("Themes & Appearance", Icons.Default.Palette, Screen.Themes),
        DrawerMenuItem("Admin Center", Icons.Default.AdminPanelSettings, Screen.AdminCenter),
        DrawerMenuItem("Settings & Preferences", Icons.Default.Settings, Screen.Settings),
        DrawerMenuItem("Help & Support Desk", Icons.Default.HelpOutline, Screen.CivicAssistant),
        DrawerMenuItem("Sign Out", Icons.Default.Logout, null, isDestructive = true)
    )

    AnimatedVisibility(
        visible = isOpen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.65f))
                .clickable { onClose() }
        )
    }

    AnimatedVisibility(
        visible = isOpen,
        enter = slideInHorizontally(initialOffsetX = { -it }),
        exit = slideOutHorizontally(targetOffsetX = { -it })
    ) {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(310.dp)
                .statusBarsPadding()
                .navigationBarsPadding(),
            color = drawerBg,
            tonalElevation = 16.dp,
            border = androidx.compose.foundation.BorderStroke(1.dp, drawerBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp, horizontal = 14.dp)
            ) {
                // Header in Drawer
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable {
                                onClose()
                                onNavigate(Screen.Profile)
                            }
                            .testTag("drawer_profile_header")
                    ) {
                        CivicPulseLogo(size = 38.dp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "CIVIC PULSE",
                                color = CivicCyan,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = user?.name ?: "Resident Citizen",
                                color = if (isDarkMode) Color(0xFFCBD5E1) else Color(0xFF475569),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Quick Dark/Light mode toggle in Drawer Header
                    IconButton(
                        onClick = onToggleDarkMode,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("drawer_theme_toggle")
                    ) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Appearance",
                            tint = if (isDarkMode) Color(0xFFFBBF24) else Color(0xFF1E40AF),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                HorizontalDivider(color = dividerColor)
                Spacer(modifier = Modifier.height(8.dp))

                // Menu items list
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(menuItems) { item ->
                        val itemColor = when {
                            item.isDestructive -> Color(0xFFEF4444)
                            item.isHighlighted -> if (isDarkMode) CivicCyan else CivicRoyalBlue
                            else -> itemTextColor
                        }
                        val itemBg = if (item.isHighlighted) {
                            if (isDarkMode) Color(0x1F06B6D4) else Color(0x152563EB)
                        } else Color.Transparent

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(itemBg)
                                .clickable {
                                    onClose()
                                    if (item.screen != null) {
                                        onNavigate(item.screen)
                                    } else if (item.isDestructive) {
                                        onLogout()
                                    }
                                }
                                .padding(horizontal = 12.dp, vertical = 11.dp)
                                .testTag("drawer_item_${item.title}")
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = itemColor,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Text(
                                text = item.title,
                                color = itemColor,
                                fontSize = 13.5.sp,
                                fontWeight = if (item.isHighlighted) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = dividerColor)
                Spacer(modifier = Modifier.height(10.dp))

                // Campaign tag at bottom
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Together for a Better Tomorrow",
                        color = if (isDarkMode) Color(0xFF64748B) else Color(0xFF94A3B8),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = if (isDarkMode) "🌙 Dark" else "☀️ Light",
                        color = CivicCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
