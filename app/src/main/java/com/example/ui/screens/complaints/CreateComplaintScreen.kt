package com.example.ui.screens.complaints

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.data.model.Complaint
import com.example.data.model.ComplaintCategory
import com.example.ui.components.CivicButton
import com.example.ui.components.CivicGlassCard
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite

@Composable
fun CreateComplaintScreen(
    onSubmit: (title: String, description: String, category: ComplaintCategory, location: String) -> Complaint?,
    onComplaintCreated: (Complaint) -> Unit,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(ComplaintCategory.ROADS) }
    var location by remember { mutableStateOf("Corner of 5th Ave & Pine St, Ward 4") }
    var isPublicVisibility by remember { mutableStateOf(true) }
    var photoAttached by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var categoryExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF070E22))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CivicCyan)
                }
                Text(
                    text = "LODGE CIVIC GRIEVANCE",
                    color = CivicWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            CivicGlassCard(modifier = Modifier.fillMaxWidth()) {
                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = Color(0xFFEF4444),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }

                // Issue Title
                Text("Issue Title", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it; errorMessage = null },
                    placeholder = { Text("e.g. Major Water Leakage on Sidewalk", color = Color(0xFF64748B), fontSize = 13.sp) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CivicCyan,
                        unfocusedBorderColor = Color(0x6638BDF8),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("complaint_title_input")
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Category Selector
                Text("Civic Category", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFF0F1A3A),
                        border = BorderStroke(1.dp, Color(0x6638BDF8)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { categoryExpanded = true }
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(selectedCategory.icon, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(selectedCategory.displayName, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                            }
                            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = CivicCyan)
                        }
                    }

                    DropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false },
                        modifier = Modifier.background(Color(0xFF0D1733))
                    ) {
                        ComplaintCategory.values().forEach { cat ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(cat.icon, fontSize = 16.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(cat.displayName, color = Color.White, fontSize = 13.sp)
                                    }
                                },
                                onClick = {
                                    selectedCategory = cat
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Description
                Text("Detailed Description", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it; errorMessage = null },
                    placeholder = { Text("Describe the civic problem, duration, hazards, and exact landmark...", color = Color(0xFF64748B), fontSize = 13.sp) },
                    minLines = 3,
                    maxLines = 5,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CivicCyan,
                        unfocusedBorderColor = Color(0x6638BDF8),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("complaint_desc_input")
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Location
                Text("Incident Location", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    leadingIcon = { Icon(Icons.Default.Place, contentDescription = null, tint = CivicCyan) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = CivicCyan,
                        unfocusedBorderColor = Color(0x6638BDF8),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("complaint_location_input")
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Photo Evidence Attachment
                Text("Photographic Evidence", color = Color(0xFF94A3B8), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFF0F1A3A),
                    border = BorderStroke(1.dp, Color(0x4D38BDF8)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { photoAttached = !photoAttached }
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(CivicRoyalBlue.copy(alpha = 0.3f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CameraAlt, contentDescription = null, tint = CivicCyan)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = if (photoAttached) "site_photo_defect.jpg (Geo-tagged)" else "Attach site defect photo",
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = if (photoAttached) "2.4 MB • GPS lat: 12.9716, lon: 77.5946" else "Tap to attach camera/gallery evidence",
                                color = if (photoAttached) Color(0xFF10B981) else Color(0xFF64748B),
                                fontSize = 11.sp
                            )
                        }
                        if (photoAttached) {
                            Icon(Icons.Default.CheckCircle, contentDescription = "Attached", tint = Color(0xFF10B981))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Visibility Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Public Community Visibility", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        Text("Allow neighbors to upvote and comment", color = Color(0xFF94A3B8), fontSize = 11.sp)
                    }
                    Switch(
                        checked = isPublicVisibility,
                        onCheckedChange = { isPublicVisibility = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = CivicCyan, checkedTrackColor = CivicRoyalBlue)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Submit Button
                CivicButton(
                    text = "SUBMIT GRIEVANCE",
                    testTag = "submit_complaint_button",
                    onClick = {
                        val created = onSubmit(title, description, selectedCategory, location)
                        if (created != null) {
                            onComplaintCreated(created)
                        } else {
                            errorMessage = "Please enter an issue title and description."
                        }
                    }
                )
            }
        }
    }
}
