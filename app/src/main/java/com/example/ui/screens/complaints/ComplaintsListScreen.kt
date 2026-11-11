package com.example.ui.screens.complaints

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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Complaint
import com.example.data.model.ComplaintCategory
import com.example.data.model.ComplaintStatus
import com.example.ui.screens.home.ComplaintCardItem
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite

@Composable
fun ComplaintsListScreen(
    complaints: List<Complaint>,
    onSelectComplaint: (Complaint) -> Unit,
    onUpvoteComplaint: (String) -> Unit,
    onCreateNewComplaint: () -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<ComplaintStatus?>(null) }
    var selectedCategory by remember { mutableStateOf<ComplaintCategory?>(null) }

    val filteredList = remember(complaints, searchQuery, selectedStatus, selectedCategory) {
        complaints.filter { cmp ->
            (searchQuery.isEmpty() || cmp.title.contains(searchQuery, ignoreCase = true) || cmp.trackingId.contains(searchQuery, ignoreCase = true)) &&
            (selectedStatus == null || cmp.status == selectedStatus) &&
            (selectedCategory == null || cmp.category == selectedCategory)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070E22))
            .statusBarsPadding()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            item {
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
                        text = "CIVIC GRIEVANCES",
                        color = CivicWhite,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                // Search Bar
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search by tracking ID or keyword...", color = Color(0xFF64748B), fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = CivicCyan) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = CivicCyan,
                            unfocusedBorderColor = Color(0x6638BDF8),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("complaints_search_input")
                    )
                }

                // Status Filter Chips
                LazyRow(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (selectedStatus == null) CivicRoyalBlue else Color(0xFF0D1733),
                            border = BorderStroke(1.dp, if (selectedStatus == null) CivicCyan else Color(0x3338BDF8)),
                            modifier = Modifier.clickable { selectedStatus = null }
                        ) {
                            Text(
                                text = "All Statuses (${complaints.size})",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }

                    items(ComplaintStatus.values()) { status ->
                        val isSelected = selectedStatus == status
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isSelected) CivicRoyalBlue else Color(0xFF0D1733),
                            border = BorderStroke(1.dp, if (isSelected) CivicCyan else Color(0x3338BDF8)),
                            modifier = Modifier.clickable { selectedStatus = status }
                        ) {
                            Text(
                                text = status.label,
                                color = if (isSelected) Color.White else Color(0xFF94A3B8),
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
            }

            if (filteredList.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📋", fontSize = 42.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Complaints Found",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "No civic issues match your current filters.",
                            color = Color(0xFF94A3B8),
                            fontSize = 13.sp
                        )
                    }
                }
            } else {
                items(filteredList) { complaint ->
                    ComplaintCardItem(
                        complaint = complaint,
                        onClick = { onSelectComplaint(complaint) },
                        onUpvote = { onUpvoteComplaint(complaint.id) }
                    )
                }
            }
        }

        // Floating Action Button to create a new complaint
        FloatingActionButton(
            onClick = onCreateNewComplaint,
            containerColor = CivicCyan,
            contentColor = Color(0xFF070E22),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .testTag("fab_create_complaint")
        ) {
            Icon(Icons.Default.Add, contentDescription = "New Complaint")
        }
    }
}
