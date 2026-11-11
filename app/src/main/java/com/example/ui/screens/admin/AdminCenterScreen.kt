package com.example.ui.screens.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.data.model.ComplaintStatus
import com.example.ui.components.CivicButton
import com.example.ui.components.CivicGlassCard
import com.example.ui.components.CivicStatusBadge
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite

@Composable
fun AdminCenterScreen(
    complaints: List<Complaint>,
    onUpdateComplaintStatus: (complaintId: String, status: ComplaintStatus, note: String) -> Unit,
    onBack: () -> Unit
) {
    var selectedComplaintToEdit by remember { mutableStateOf<Complaint?>(complaints.firstOrNull()) }
    var selectedNewStatus by remember { mutableStateOf(ComplaintStatus.RESOLVED) }
    var officialNoteInput by remember { mutableStateOf("Works inspection verified. Surface repaired and traffic restored.") }

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
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CivicCyan)
                    }
                    Text(
                        text = "ADMINISTRATIVE CONSOLE",
                        color = CivicWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Admin Overview Banner
                CivicGlassCard(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = CivicCyan, modifier = Modifier.size(28.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text("Ward Grievance Officer Mode", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Text("Official municipality status authorization", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Action form: Update selected complaint status
                if (selectedComplaintToEdit != null) {
                    val cmp = selectedComplaintToEdit!!
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF0D1733),
                        border = BorderStroke(1.dp, CivicCyan.copy(alpha = 0.6f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("UPDATE COMPLAINT STATUS", color = CivicCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("${cmp.trackingId}: ${cmp.title}", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Current: ${cmp.status.label}", color = Color(0xFF94A3B8), fontSize = 11.sp)

                            Spacer(modifier = Modifier.height(12.dp))

                            Text("Select New Status", color = Color(0xFFCBD5E1), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                listOf(ComplaintStatus.UNDER_REVIEW, ComplaintStatus.IN_PROGRESS, ComplaintStatus.RESOLVED).forEach { st ->
                                    val isSel = selectedNewStatus == st
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isSel) CivicRoyalBlue else Color(0xFF0F1A3A),
                                        border = BorderStroke(1.dp, if (isSel) CivicCyan else Color(0x3338BDF8)),
                                        modifier = Modifier.clickable { selectedNewStatus = st }
                                    ) {
                                        Text(
                                            text = st.label,
                                            color = if (isSel) Color.White else Color(0xFF94A3B8),
                                            fontSize = 11.sp,
                                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text("Official Department Note", color = Color(0xFFCBD5E1), fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(modifier = Modifier.height(4.dp))
                            OutlinedTextField(
                                value = officialNoteInput,
                                onValueChange = { officialNoteInput = it },
                                singleLine = false,
                                maxLines = 3,
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = CivicCyan,
                                    unfocusedBorderColor = Color(0x4D38BDF8),
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            CivicButton(
                                text = "COMMIT OFFICIAL UPDATE",
                                testTag = "admin_commit_status_btn",
                                onClick = {
                                    onUpdateComplaintStatus(cmp.id, selectedNewStatus, officialNoteInput)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))
                }

                Text(
                    text = "SELECT COMPLAINT TO MANAGE",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            items(complaints) { item ->
                val isSelected = selectedComplaintToEdit?.id == item.id
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { selectedComplaintToEdit = item }
                        .testTag("admin_item_${item.id}"),
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF0D1733),
                    border = BorderStroke(1.dp, if (isSelected) CivicCyan else Color(0x2238BDF8))
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.trackingId, color = CivicCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            Text(item.title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text(item.department, color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                        CivicStatusBadge(status = item.status)
                    }
                }
            }
        }
    }
}
