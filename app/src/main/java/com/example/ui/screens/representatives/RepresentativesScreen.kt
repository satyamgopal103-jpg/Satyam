package com.example.ui.screens.representatives

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
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Representative
import com.example.ui.components.CivicGlassCard
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite

@Composable
fun RepresentativesScreen(
    representatives: List<Representative>,
    onBack: () -> Unit
) {
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
                        text = "TRANSPARENCY & LEADERS",
                        color = CivicWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                CivicGlassCard(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "CIVIC TRANSPARENCY CHARTER",
                        color = CivicCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Public representative ratings are derived strictly from documented council votes, verified project completion milestones, and statutory grievance response times.",
                        color = Color(0xFFCBD5E1),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "ELECTED REPRESENTATIVES",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            items(representatives) { rep ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .testTag("rep_card_${rep.id}"),
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFF0D1733),
                    border = BorderStroke(1.dp, Color(0x4D38BDF8))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF1E3A8A))
                                    .border(2.dp, CivicCyan, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = rep.name.firstOrNull()?.toString() ?: "R",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = rep.name,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (rep.verified) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            Icons.Default.Verified,
                                            contentDescription = "Verified Official",
                                            tint = CivicCyan,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                                Text(rep.title, color = CivicCyan, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                Text("${rep.constituency} • ${rep.party}", color = Color(0xFF94A3B8), fontSize = 11.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        HorizontalDivider(color = Color(0x2238BDF8))
                        Spacer(modifier = Modifier.height(12.dp))

                        // Stats Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Response SLA", color = Color(0xFF94A3B8), fontSize = 10.sp)
                                Text("${rep.responseRatePercent}%", color = Color(0xFF10B981), fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                            Column {
                                Text("Completed Projects", color = Color(0xFF94A3B8), fontSize = 10.sp)
                                Text("${rep.projectsCompleted}", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                            Column {
                                Text("Grievances Handled", color = Color(0xFF94A3B8), fontSize = 10.sp)
                                Text("${rep.activeComplaintsHandled}", color = CivicCyan, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text("Grant Deployed: ${rep.totalGrantUtilized}", color = Color(0xFFCBD5E1), fontSize = 11.sp)

                        if (rep.recentActivities.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("DOCUMENTED PUBLIC ACTIVITIES", color = Color(0xFF64748B), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(4.dp))
                            rep.recentActivities.forEach { act ->
                                Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                    Text("• ", color = CivicCyan, fontSize = 11.sp)
                                    Text(act, color = Color(0xFFCBD5E1), fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
