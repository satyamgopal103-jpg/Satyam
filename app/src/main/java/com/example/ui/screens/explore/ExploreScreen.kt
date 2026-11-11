package com.example.ui.screens.explore

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CivicGlassCard
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite
import com.example.viewmodel.Screen

data class ServiceCategory(
    val title: String,
    val icon: ImageVector,
    val count: String,
    val targetScreen: Screen
)

@Composable
fun ExploreScreen(
    onNavigate: (Screen) -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val categories = listOf(
        ServiceCategory("Grievances & Complaints", Icons.Default.ReportProblem, "24 Active", Screen.Complaints),
        ServiceCategory("Civic Projects", Icons.Default.Engineering, "18 Running", Screen.Projects),
        ServiceCategory("Elected Representatives", Icons.Default.AccountBalance, "3 Verified", Screen.Representatives),
        ServiceCategory("Community Groups", Icons.Default.Groups, "12 Wards", Screen.Communities),
        ServiceCategory("Polls & Consultations", Icons.Default.HowToVote, "2 Open", Screen.Polls),
        ServiceCategory("Volunteer Drives", Icons.Default.VolunteerActivism, "5 Drives", Screen.Volunteering),
        ServiceCategory("Civic Assistant AI", Icons.Default.SmartToy, "Online", Screen.CivicAssistant),
        ServiceCategory("Interactive City Map", Icons.Default.Map, "GPS Ready", Screen.CivicMap)
    )

    val filterChips = listOf("All", "Services", "Projects", "Governance", "Community", "Safety")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070E22))
            .statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CivicCyan)
                    }
                    Text(
                        text = "EXPLORE CIVIC SERVICES",
                        color = CivicWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search services, departments, projects, w... ", color = Color(0xFF64748B), fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = CivicCyan) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Close, contentDescription = "Clear", tint = Color(0xFF94A3B8))
                            }
                        }
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CivicCyan,
                        unfocusedBorderColor = Color(0x6638BDF8),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("explore_search_input")
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Filter Row
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(filterChips) { chip ->
                        val isSelected = selectedFilter == chip
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) CivicRoyalBlue else Color(0xFF0D1733),
                            border = BorderStroke(1.dp, if (isSelected) CivicCyan else Color(0x3338BDF8)),
                            modifier = Modifier.clickable { selectedFilter = chip }
                        ) {
                            Text(
                                text = chip,
                                color = if (isSelected) Color.White else Color(0xFF94A3B8),
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "DISCOVER DIRECTORY",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(10.dp))
            }

            // Grid items as full width cards
            items(categories.filter {
                searchQuery.isEmpty() || it.title.contains(searchQuery, ignoreCase = true)
            }) { cat ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clickable { onNavigate(cat.targetScreen) }
                        .testTag("explore_item_${cat.title}"),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF0D1733),
                    border = BorderStroke(1.dp, Color(0x3338BDF8))
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(CivicRoyalBlue.copy(alpha = 0.25f), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(cat.icon, contentDescription = cat.title, tint = CivicCyan, modifier = Modifier.size(24.dp))
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = cat.title, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(text = cat.count, color = CivicCyan, fontSize = 11.sp)
                        }

                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color(0xFF64748B))
                    }
                }
            }
        }
    }
}
