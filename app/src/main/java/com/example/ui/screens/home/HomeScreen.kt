package com.example.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import com.example.viewmodel.Screen

@Composable
fun HomeScreen(
    currentUser: User,
    complaints: List<Complaint>,
    representatives: List<Representative>,
    projects: List<CivicProject>,
    news: List<CivicNews>,
    unreadNotificationsCount: Int,
    isDarkMode: Boolean = true,
    onMenuClick: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onSelectComplaint: (Complaint) -> Unit,
    onUpvoteComplaint: (String) -> Unit,
    onToggleDarkMode: () -> Unit = {}
) {
    var selectedCategoryFilter by remember { mutableStateOf<ComplaintCategory?>(null) }
    var selectedMlaFilter by remember { mutableStateOf("Monthly") }

    val filteredComplaints = remember(selectedCategoryFilter, complaints) {
        if (selectedCategoryFilter == null) complaints
        else complaints.filter { it.category == selectedCategoryFilter }
    }

    val screenBg = if (isDarkMode) Color(0xFF070E22) else Color(0xFFF1F5F9)

    Box(modifier = Modifier.fillMaxSize().background(screenBg)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            // 1. Header
            item {
                CivicTopBar(
                    userName = currentUser.name.split(" ").getOrNull(1) ?: "Citizen",
                    unreadNotificationsCount = unreadNotificationsCount,
                    isDarkMode = isDarkMode,
                    onMenuClick = onMenuClick,
                    onNotificationsClick = { onNavigate(Screen.Notifications) },
                    onProfileClick = { onNavigate(Screen.Profile) },
                    onSearchClick = { onNavigate(Screen.Explore) },
                    onToggleDarkMode = onToggleDarkMode
                )
            }

            // 2. Hero Banner
            item {
                HeroBannerSection(
                    onExploreClick = { onNavigate(Screen.Explore) },
                    onJoinMovement = { onNavigate(Screen.Volunteering) }
                )
            }

            // 3. Quick Actions
            item {
                QuickActionsSection(
                    onReportIssue = { onNavigate(Screen.CreateComplaint) },
                    onFindServices = { onNavigate(Screen.Explore) },
                    onJoinCommunity = { onNavigate(Screen.Communities) },
                    onEvents = { onNavigate(Screen.Volunteering) },
                    onVolunteer = { onNavigate(Screen.Volunteering) },
                    onProjects = { onNavigate(Screen.Projects) }
                )
            }

            // 4. Civic Dashboard Metrics
            item {
                CivicMetricsSection()
            }

            // 5. Nearby Issues
            item {
                NearbyIssuesHeader(
                    selectedFilter = selectedCategoryFilter,
                    onSelectFilter = { selectedCategoryFilter = if (selectedCategoryFilter == it) null else it },
                    onViewFullMap = { onNavigate(Screen.CivicMap) }
                )
            }

            items(filteredComplaints.take(3)) { complaint ->
                ComplaintCardItem(
                    complaint = complaint,
                    onClick = { onSelectComplaint(complaint) },
                    onUpvote = { onUpvoteComplaint(complaint.id) }
                )
            }

            // 6. MLA & Transparency Leaderboard
            item {
                MlaSection(
                    selectedPeriod = selectedMlaFilter,
                    onSelectPeriod = { selectedMlaFilter = it },
                    representatives = representatives,
                    onViewAll = { onNavigate(Screen.Representatives) }
                )
            }

            // 7. News & Public Announcements
            item {
                NewsSection(
                    news = news,
                    onViewAll = { onNavigate(Screen.Explore) }
                )
            }

            // 8. Be a Part of Change Call to Action
            item {
                ChangeCtaSection(
                    onVolunteer = { onNavigate(Screen.Volunteering) },
                    onPolls = { onNavigate(Screen.Polls) }
                )
            }
        }
    }
}

@Composable
private fun HeroBannerSection(
    onExploreClick: () -> Unit,
    onJoinMovement: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(22.dp),
        color = Color.Transparent,
        border = BorderStroke(1.dp, Color(0x4D38BDF8))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF0F265C),
                            Color(0xFF08183A),
                            Color(0xFF070E22)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(CivicCyan.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "CAMPAIGN INITIATIVE",
                            color = CivicCyanBright,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Together for a Better Tomorrow.",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Discover initiatives, share ideas, and make your neighbourhood safer, greener, and more connected.",
                    color = Color(0xFFCBD5E1),
                    fontSize = 13.sp,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = onExploreClick,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CivicCyan),
                        modifier = Modifier.height(42.dp)
                    ) {
                        Text("Explore Initiatives", color = Color(0xFF070E22), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }

                    OutlinedButton(
                        onClick = onJoinMovement,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0x8038BDF8)),
                        modifier = Modifier.height(42.dp)
                    ) {
                        Text("Join Movement", color = CivicCyanBright, fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickActionsSection(
    onReportIssue: () -> Unit,
    onFindServices: () -> Unit,
    onJoinCommunity: () -> Unit,
    onEvents: () -> Unit,
    onVolunteer: () -> Unit,
    onProjects: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(
            text = "QUICK ACTIONS",
            color = Color(0xFF94A3B8),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        val actions = listOf(
            Triple("Report Issue", Icons.Default.ReportProblem, onReportIssue),
            Triple("Find Services", Icons.Default.Search, onFindServices),
            Triple("Join Community", Icons.Default.Groups, onJoinCommunity),
            Triple("Events", Icons.Default.Event, onEvents),
            Triple("Volunteer", Icons.Default.VolunteerActivism, onVolunteer),
            Triple("Projects", Icons.Default.Engineering, onProjects)
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            actions.take(3).forEach { (label, icon, onClick) ->
                QuickActionItem(label, icon, onClick, modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            actions.drop(3).forEach { (label, icon, onClick) ->
                QuickActionItem(label, icon, onClick, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun QuickActionItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF0F1A3A))
            .border(1.dp, Color(0x3338BDF8), RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp, horizontal = 4.dp)
            .testTag("quick_action_$label")
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(0xFF1E3A8A)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = CivicCyan,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun CivicMetricsSection() {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CIVIC DASHBOARD",
                color = Color(0xFF94A3B8),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "Ward 4 • Metro North",
                color = CivicCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(
                title = "Engagement",
                value = "88%",
                sub = "+6% this month",
                color = Color(0xFF10B981),
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "New Issues",
                value = "24",
                sub = "3 in your ward",
                color = Color(0xFF38BDF8),
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Resolved",
                value = "182",
                sub = "92% SLA met",
                color = Color(0xFF6366F1),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            MetricCard(
                title = "Active Events",
                value = "8",
                sub = "4 this weekend",
                color = Color(0xFFF59E0B),
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Participants",
                value = "1,420",
                sub = "Active citizens",
                color = Color(0xFFEC4899),
                modifier = Modifier.weight(1f)
            )
            MetricCard(
                title = "Project Pace",
                value = "74%",
                sub = "Public works avg",
                color = Color(0xFF14B8A6),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    sub: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = Color(0xFF0D1733),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, color = Color(0xFF94A3B8), fontSize = 11.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = sub, color = color, fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun NearbyIssuesHeader(
    selectedFilter: ComplaintCategory?,
    onSelectFilter: (ComplaintCategory) -> Unit,
    onViewFullMap: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "NEARBY CIVIC ISSUES",
                color = Color(0xFF94A3B8),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "View Map",
                color = CivicCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onViewFullMap() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Category Filter Chips
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(ComplaintCategory.values()) { cat ->
                val isSelected = selectedFilter == cat
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) CivicCyan else Color(0xFF0F1A3A),
                    border = BorderStroke(1.dp, if (isSelected) CivicCyan else Color(0x3338BDF8)),
                    modifier = Modifier.clickable { onSelectFilter(cat) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(cat.icon, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = cat.displayName,
                            color = if (isSelected) Color(0xFF070E22) else Color.White,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ComplaintCardItem(
    complaint: Complaint,
    onClick: () -> Unit,
    onUpvote: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onClick)
            .testTag("complaint_card_${complaint.id}"),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF0D1733),
        border = BorderStroke(1.dp, Color(0x3338BDF8))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(complaint.category.icon, fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = complaint.trackingId,
                        color = CivicCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                CivicStatusBadge(status = complaint.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = complaint.title,
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = complaint.description,
                color = Color(0xFF94A3B8),
                fontSize = 13.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = "Location",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = complaint.location,
                        color = Color(0xFF94A3B8),
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.widthIn(max = 200.dp)
                    )
                }

                // Upvote Button
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (complaint.isUpvotedByMe) CivicRoyalBlue.copy(alpha = 0.3f) else Color(0x1F38BDF8),
                    border = BorderStroke(1.dp, if (complaint.isUpvotedByMe) CivicCyan else Color(0x3338BDF8)),
                    modifier = Modifier.clickable { onUpvote() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            Icons.Default.ThumbUp,
                            contentDescription = "Upvote",
                            tint = if (complaint.isUpvotedByMe) CivicCyan else Color(0xFFCBD5E1),
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${complaint.upvotes}",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MlaSection(
    selectedPeriod: String,
    onSelectPeriod: (String) -> Unit,
    representatives: List<Representative>,
    onViewAll: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "MLA & LEADERS TRANSPARENCY",
                color = Color(0xFF94A3B8),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "Leaderboard",
                color = CivicCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onViewAll() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Time Filters: Monthly, Yearly, All-Time
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Monthly", "Yearly", "All-Time").forEach { period ->
                val active = selectedPeriod == period
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (active) CivicRoyalBlue else Color(0xFF0F1A3A))
                        .clickable { onSelectPeriod(period) }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = period,
                        color = if (active) Color.White else Color(0xFF94A3B8),
                        fontSize = 11.sp,
                        fontWeight = if (active) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        representatives.take(2).forEach { rep ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFF0D1733),
                border = BorderStroke(1.dp, Color(0x3338BDF8))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF1E3A8A))
                            .border(1.5.dp, CivicCyan, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(rep.name.firstOrNull()?.toString() ?: "R", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(rep.name, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.Verified, contentDescription = "Verified", tint = CivicCyan, modifier = Modifier.size(15.dp))
                        }
                        Text(rep.title, color = Color(0xFF94A3B8), fontSize = 11.sp)
                        Text(rep.constituency, color = CivicCyan, fontSize = 11.sp)
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text("${rep.responseRatePercent}%", color = Color(0xFF10B981), fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("Response SLA", color = Color(0xFF64748B), fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsSection(
    news: List<CivicNews>,
    onViewAll: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "NEWS & ANNOUNCEMENTS",
                color = Color(0xFF94A3B8),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "All Updates",
                color = CivicCyan,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { onViewAll() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        news.take(2).forEach { item ->
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFF0D1733),
                border = BorderStroke(1.dp, Color(0x3338BDF8))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(item.source, color = CivicCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(item.date, color = Color(0xFF64748B), fontSize = 11.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(item.title, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(item.summary, color = Color(0xFF94A3B8), fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                }
            }
        }
    }
}

@Composable
private fun ChangeCtaSection(
    onVolunteer: () -> Unit,
    onPolls: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFF0A1530),
        border = BorderStroke(1.dp, Color(0x4D38BDF8))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "BE A PART OF CHANGE",
                color = CivicCyanBright,
                fontSize = 13.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Join over 1,400 active residents shaping local budgets, reporting defects, and preserving green spaces.",
                color = Color(0xFFCBD5E1),
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = onVolunteer,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CivicRoyalBlue),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Volunteer", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                OutlinedButton(
                    onClick = onPolls,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, CivicCyan),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Vote in Polls", color = CivicCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
