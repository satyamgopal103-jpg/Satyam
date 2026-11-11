package com.example.ui.screens.maps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Complaint
import com.example.ui.components.CivicGlassCard
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite

data class MapPin(
    val id: String,
    val title: String,
    val category: String,
    val xRatio: Float,
    val yRatio: Float,
    val color: Color,
    val icon: String,
    val details: String
)

@Composable
fun CivicMapScreen(
    complaints: List<Complaint>,
    onSelectComplaint: (Complaint) -> Unit,
    onBack: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("All") }
    var userLocationEnabled by remember { mutableStateOf(true) }

    val pins = listOf(
        MapPin("p-1", "Potholes on 5th Main", "Roads", 0.35f, 0.42f, Color(0xFFEF4444), "🛣️", "CP-2026-8491 • In Progress"),
        MapPin("p-2", "Broken Streetlight", "Lighting", 0.62f, 0.32f, Color(0xFFF59E0B), "💡", "Maple Park West • Resolved"),
        MapPin("p-3", "Overflowing Storm Drain", "Drainage", 0.48f, 0.68f, Color(0xFF06B6D4), "🌊", "Pine Blvd Corner • Under Review"),
        MapPin("p-4", "Green Corridor Project", "Projects", 0.75f, 0.55f, Color(0xFF10B981), "🏗️", "Riverfront Trail • 78% Complete"),
        MapPin("p-5", "Ward 4 Municipal Office", "Offices", 0.22f, 0.28f, Color(0xFF8B5CF6), "🏛️", "Open 9:00 AM – 5:30 PM")
    )

    var selectedPin by remember { mutableStateOf<MapPin?>(pins[0]) }

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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CivicCyan)
                    }
                    Text(
                        text = "CIVIC EXPLORER MAP",
                        color = CivicWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                // GPS Target Button
                IconButton(
                    onClick = { userLocationEnabled = !userLocationEnabled },
                    modifier = Modifier
                        .size(38.dp)
                        .background(if (userLocationEnabled) CivicRoyalBlue else Color(0xFF0D1733), CircleShape)
                        .testTag("gps_my_location_button")
                ) {
                    Icon(
                        Icons.Default.MyLocation,
                        contentDescription = "My Location",
                        tint = if (userLocationEnabled) CivicCyan else Color(0xFF94A3B8),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Category Filters
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listOf("All", "Roads", "Lighting", "Drainage", "Projects", "Offices")) { filter ->
                    val isSel = selectedFilter == filter
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSel) CivicRoyalBlue else Color(0xFF0D1733),
                        border = BorderStroke(1.dp, if (isSel) CivicCyan else Color(0x3338BDF8)),
                        modifier = Modifier.clickable { selectedFilter = filter }
                    ) {
                        Text(
                            text = filter,
                            color = if (isSel) Color.White else Color(0xFF94A3B8),
                            fontSize = 12.sp,
                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Interactive Map Canvas
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF09132B))
                    .border(1.dp, Color(0x4D38BDF8), RoundedCornerShape(20.dp))
            ) {
                // Map background roads & grid canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Grid lines (city streets)
                    val streetColor = Color(0x2238BDF8)
                    for (x in 0..6) {
                        drawLine(streetColor, Offset(x * (w / 6), 0f), Offset(x * (w / 6), h), strokeWidth = 2.5f)
                    }
                    for (y in 0..8) {
                        drawLine(streetColor, Offset(0f, y * (h / 8)), Offset(w, y * (h / 8)), strokeWidth = 2.5f)
                    }

                    // Main Arterial Highway
                    drawLine(
                        Color(0xFF1E3A8A),
                        Offset(0f, h * 0.45f),
                        Offset(w, h * 0.52f),
                        strokeWidth = 10f
                    )
                    drawLine(
                        Color(0xFF06B6D4).copy(alpha = 0.6f),
                        Offset(0f, h * 0.45f),
                        Offset(w, h * 0.52f),
                        strokeWidth = 2f
                    )

                    // River / Canal blue curve
                    drawLine(
                        Color(0xFF0284C7).copy(alpha = 0.45f),
                        Offset(w * 0.8f, 0f),
                        Offset(w * 0.65f, h),
                        strokeWidth = 16f
                    )
                }

                // Resident My Location beacon
                if (userLocationEnabled) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(24.dp)
                            .background(CivicCyan.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(CivicCyan, CircleShape)
                                .border(2.dp, Color.White, CircleShape)
                        )
                    }
                }

                // Interactive Map Pins
                pins.filter { selectedFilter == "All" || it.category == selectedFilter }.forEach { pin ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .offset(x = (pin.xRatio * 260).dp, y = (pin.yRatio * 340).dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(pin.color)
                                .border(1.5.dp, Color.White, RoundedCornerShape(8.dp))
                                .clickable { selectedPin = pin }
                                .padding(horizontal = 6.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(pin.icon, fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = pin.title.take(12),
                                    color = Color.White,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                // Selected Marker Details Card (docked at bottom of map)
                if (selectedPin != null) {
                    val p = selectedPin!!
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xEE070E22),
                        border = BorderStroke(1.dp, p.color),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(p.icon, fontSize = 26.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(p.title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Text(p.details, color = CivicCyan, fontSize = 11.sp)
                            }
                            Button(
                                onClick = {
                                    val matched = complaints.find { it.title.contains(p.title.take(8)) }
                                    if (matched != null) onSelectComplaint(matched)
                                },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = CivicRoyalBlue),
                                modifier = Modifier.height(34.dp)
                            ) {
                                Text("Inspect", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
