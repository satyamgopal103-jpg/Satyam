package com.example.ui.screens.notifications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CivicNotification
import com.example.data.model.NotificationFilter
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite

@Composable
fun NotificationsScreen(
    notifications: List<CivicNotification>,
    onMarkAllRead: () -> Unit,
    onBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf(NotificationFilter.ALL) }

    val filteredList = remember(notifications, selectedFilter) {
        if (selectedFilter == NotificationFilter.ALL) notifications
        else notifications.filter { it.category == selectedFilter }
    }

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
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CivicCyan)
                        }
                        Text(
                            text = "NOTIFICATIONS",
                            color = CivicWhite,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }

                    TextButton(onClick = onMarkAllRead) {
                        Icon(Icons.Default.DoneAll, contentDescription = null, tint = CivicCyan, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Mark Read", color = CivicCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Filter Categories
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(NotificationFilter.values()) { filter ->
                        val isSel = selectedFilter == filter
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSel) CivicRoyalBlue else Color(0xFF0D1733),
                            border = BorderStroke(1.dp, if (isSel) CivicCyan else Color(0x3338BDF8)),
                            modifier = Modifier.clickable { selectedFilter = filter }
                        ) {
                            Text(
                                text = filter.label,
                                color = if (isSel) Color.White else Color(0xFF94A3B8),
                                fontSize = 12.sp,
                                fontWeight = if (isSel) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
            }

            if (filteredList.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🔔", fontSize = 36.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("No notifications", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        Text("You're completely up to date with your community.", color = Color(0xFF94A3B8), fontSize = 12.sp)
                    }
                }
            } else {
                items(filteredList) { notif ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .testTag("notif_item_${notif.id}"),
                        shape = RoundedCornerShape(14.dp),
                        color = if (notif.isRead) Color(0xFF0D1733) else Color(0xFF0F1E44),
                        border = BorderStroke(1.dp, if (!notif.isRead) CivicCyan.copy(alpha = 0.5f) else Color(0x2238BDF8))
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            if (!notif.isRead) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .size(8.dp)
                                        .background(CivicCyan, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = notif.title,
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = if (!notif.isRead) FontWeight.Bold else FontWeight.SemiBold
                                    )
                                    Text(notif.timestamp, color = Color(0xFF64748B), fontSize = 10.sp)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = notif.message,
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
    }
}
