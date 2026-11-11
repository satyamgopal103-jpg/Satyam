package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.viewmodel.Screen

@Composable
fun CivicBottomNav(
    currentScreen: Screen,
    isDarkMode: Boolean = true,
    onNavigate: (Screen) -> Unit
) {
    val navBgColor = if (isDarkMode) Color(0xF2070E22) else Color(0xF8FFFFFF)
    val navBorderColor = if (isDarkMode) Color(0x3338BDF8) else Color(0xFFCBD5E1)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        color = navBgColor,
        tonalElevation = 8.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, navBorderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Home
            NavItem(
                label = "Home",
                icon = Icons.Default.Home,
                selected = currentScreen is Screen.Home,
                isDarkMode = isDarkMode,
                onClick = { onNavigate(Screen.Home) },
                testTag = "nav_home"
            )

            // 2. Explore
            NavItem(
                label = "Explore",
                icon = Icons.Default.Explore,
                selected = currentScreen is Screen.Explore,
                isDarkMode = isDarkMode,
                onClick = { onNavigate(Screen.Explore) },
                testTag = "nav_explore"
            )

            // 3. Create (Accented Center Button)
            Box(
                modifier = Modifier
                    .offset(y = (-10).dp)
                    .size(56.dp)
                    .shadow(12.dp, CircleShape, spotColor = CivicCyan)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(CivicRoyalBlue, CivicCyan)
                        )
                    )
                    .border(2.dp, Color.White.copy(alpha = 0.9f), CircleShape)
                    .clickable { onNavigate(Screen.CreateComplaint) }
                    .testTag("nav_create_center"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Report Issue",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }

            // 4. Notifications
            NavItem(
                label = "Alerts",
                icon = Icons.Default.Notifications,
                selected = currentScreen is Screen.Notifications,
                isDarkMode = isDarkMode,
                onClick = { onNavigate(Screen.Notifications) },
                testTag = "nav_notifications"
            )

            // 5. Profile
            NavItem(
                label = "Profile",
                icon = Icons.Default.Person,
                selected = currentScreen is Screen.Profile,
                isDarkMode = isDarkMode,
                onClick = { onNavigate(Screen.Profile) },
                testTag = "nav_profile"
            )
        }
    }
}

@Composable
private fun NavItem(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    isDarkMode: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val unselectedColor = if (isDarkMode) Color(0xFF94A3B8) else Color(0xFF64748B)
    val activeColor = if (isDarkMode) CivicCyan else CivicRoyalBlue

    val tint by animateColorAsState(
        targetValue = if (selected) activeColor else unselectedColor,
        animationSpec = tween(200),
        label = "nav_tint"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag(testTag)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            color = tint,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
        )
    }
}
