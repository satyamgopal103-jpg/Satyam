package com.example.ui.screens.complaints

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import com.example.data.model.CommentItem
import com.example.data.model.Complaint
import com.example.ui.components.CivicGlassCard
import com.example.ui.components.CivicStatusBadge
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite

@Composable
fun ComplaintDetailScreen(
    complaint: Complaint,
    onUpvote: () -> Unit,
    onBack: () -> Unit
) {
    var newCommentText by remember { mutableStateOf("") }
    var comments by remember {
        mutableStateOf(
            listOf(
                CommentItem("c-1", "Rajesh Verma (Ward Engineer)", "Inspection completed. Pothole depth measured at 12cm. Crew dispatched with cold-mix asphalt.", "Nov 04, 2026", isOfficial = true, likes = 12),
                CommentItem("c-2", "Ananya Sen (Resident)", "Thank you! This junction was very dangerous for scooters yesterday in the downpour.", "Nov 05, 2026", isOfficial = false, likes = 5)
            )
        )
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
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            // Top Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CivicCyan)
                    }

                    Text(
                        text = complaint.trackingId,
                        color = CivicCyan,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    CivicStatusBadge(status = complaint.status)
                }
            }

            // Overview Card
            item {
                CivicGlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = complaint.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = complaint.description,
                        color = Color(0xFFCBD5E1),
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("ASSIGNED DEPARTMENT", color = Color(0xFF64748B), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Text(complaint.department, color = CivicCyan, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                        }

                        // Upvote Button
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (complaint.isUpvotedByMe) CivicRoyalBlue.copy(alpha = 0.35f) else Color(0xFF0F1A3A),
                            border = BorderStroke(1.dp, if (complaint.isUpvotedByMe) CivicCyan else Color(0x3338BDF8)),
                            modifier = Modifier.clickable { onUpvote() }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    Icons.Default.ThumbUp,
                                    contentDescription = "Upvote",
                                    tint = if (complaint.isUpvotedByMe) CivicCyan else Color(0xFFCBD5E1),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${complaint.upvotes} Supports",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    if (complaint.officialResponse != null) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0x2E10B981),
                            border = BorderStroke(1.dp, Color(0xFF10B981).copy(alpha = 0.4f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Verified, contentDescription = null, tint = Color(0xFF10B981), modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("OFFICIAL DEPARTMENT RESPONSE", color = Color(0xFF34D399), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(complaint.officialResponse, color = Color.White, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // Lifecycle Timeline
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Text(
                        text = "GRIEVANCE RESOLUTION TIMELINE",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    complaint.timeline.forEachIndexed { index, step ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clip(CircleShape)
                                        .background(CivicCyan),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                    )
                                }
                                if (index < complaint.timeline.size - 1) {
                                    Box(
                                        modifier = Modifier
                                            .width(2.dp)
                                            .height(44.dp)
                                            .background(Color(0x6638BDF8))
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.padding(bottom = 14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(step.status.label, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                    Text(step.date, color = Color(0xFF64748B), fontSize = 11.sp)
                                }
                                Text(step.note, color = Color(0xFFCBD5E1), fontSize = 12.sp)
                                Text("Updated by: ${step.actor}", color = CivicCyan, fontSize = 10.sp)
                            }
                        }
                    }
                }
            }

            // Public Discussion Section
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(
                        text = "COMMUNITY DISCUSSION (${comments.size})",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Comment Input Box
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = newCommentText,
                            onValueChange = { newCommentText = it },
                            placeholder = { Text("Write a comment or update...", color = Color(0xFF64748B), fontSize = 13.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = CivicCyan,
                                unfocusedBorderColor = Color(0x6638BDF8),
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("comment_input_field")
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                if (newCommentText.isNotBlank()) {
                                    comments = comments + CommentItem(
                                        id = "c-${System.currentTimeMillis()}",
                                        author = "Citizen Alex Morgan",
                                        text = newCommentText,
                                        date = "Just now",
                                        isOfficial = false,
                                        likes = 1
                                    )
                                    newCommentText = ""
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .background(CivicRoyalBlue, RoundedCornerShape(12.dp))
                                .testTag("send_comment_button")
                        ) {
                            Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                        }
                    }
                }
            }

            // Comments List
            items(comments) { comment ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF0D1733),
                    border = BorderStroke(1.dp, if (comment.isOfficial) Color(0x6610B981) else Color(0x2238BDF8))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(comment.author, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                if (comment.isOfficial) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(Icons.Default.Verified, contentDescription = "Official", tint = Color(0xFF10B981), modifier = Modifier.size(13.dp))
                                }
                            }
                            Text(comment.date, color = Color(0xFF64748B), fontSize = 10.sp)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(comment.text, color = Color(0xFFCBD5E1), fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
