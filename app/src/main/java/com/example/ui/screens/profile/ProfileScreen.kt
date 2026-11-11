package com.example.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
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
import com.example.data.model.User
import com.example.ui.components.CivicGlassCard
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite
import com.example.viewmodel.Screen

@Composable
fun ProfileScreen(
    user: User,
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    var publicProfileToggle by remember { mutableStateOf(user.isPublicProfile) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070E22))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CivicCyan)
                    }

                    Text(
                        text = "RESIDENT PROFILE",
                        color = CivicWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    IconButton(onClick = { onNavigate(Screen.Settings) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color(0xFFCBD5E1))
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Profile Avatar & Identity Card
                CivicGlassCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(76.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF1E3A8A))
                                .border(2.5.dp, CivicCyan, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString(""),
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(user.name, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.CheckCircle, contentDescription = "Verified Citizen", tint = CivicCyan, modifier = Modifier.size(17.dp))
                        }

                        Text(user.ward, color = CivicCyan, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        Text(user.constituency, color = Color(0xFF94A3B8), fontSize = 11.sp)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Points Banner
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF0F265C),
                            border = BorderStroke(1.dp, Color(0x6638BDF8)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("CIVIC ENGAGEMENT REWARD", color = Color(0xFF94A3B8), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    Text("Level 4 Community Steward", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                                Text("${user.points} pts", color = Color(0xFF10B981), fontSize = 20.sp, fontWeight = FontWeight.Black)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Impact Stats
                Text("MY CIVIC IMPACT", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF0D1733),
                        border = BorderStroke(1.dp, Color(0x3338BDF8))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Complaints", color = Color(0xFF94A3B8), fontSize = 11.sp)
                            Text("${user.complaintsLogged}", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("${user.complaintsResolved} resolved", color = Color(0xFF10B981), fontSize = 10.sp)
                        }
                    }

                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF0D1733),
                        border = BorderStroke(1.dp, Color(0x3338BDF8))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Volunteering", color = Color(0xFF94A3B8), fontSize = 11.sp)
                            Text("${user.volunteerHours} hrs", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("Active drives", color = CivicCyan, fontSize = 10.sp)
                        }
                    }

                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFF0D1733),
                        border = BorderStroke(1.dp, Color(0x3338BDF8))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Events", color = Color(0xFF94A3B8), fontSize = 11.sp)
                            Text("${user.eventsAttended}", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("Town halls", color = Color(0xFFF59E0B), fontSize = 10.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text("EARNED CITIZEN BADGES", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(user.badges) { badge ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = Color(0xFF0D1733),
                    border = BorderStroke(1.dp, Color(0x2238BDF8))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(badge.iconEmoji, fontSize = 26.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(badge.name, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(badge.description, color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(14.dp))

                // Privacy Settings Card
                CivicGlassCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Public Citizen Profile", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text("Show badge accolades on leaderboard", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                        Switch(
                            checked = publicProfileToggle,
                            onCheckedChange = { publicProfileToggle = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = CivicCyan, checkedTrackColor = CivicRoyalBlue)
                        )
                    }
                }
            }
        }
    }
}
