package com.example.ui.screens.polls

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Poll
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite

@Composable
fun PollsScreen(
    polls: List<Poll>,
    onVote: (pollId: String, optionId: String) -> Unit,
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
                        text = "POLLS & PUBLIC CONSULTATIONS",
                        color = CivicWhite,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }

            items(polls) { poll ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .testTag("poll_card_${poll.id}"),
                    shape = RoundedCornerShape(18.dp),
                    color = Color(0xFF0D1733),
                    border = BorderStroke(1.dp, if (poll.isOfficialConsultation) CivicCyan else Color(0x3338BDF8))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (poll.isOfficialConsultation) "OFFICIAL CONSULTATION" else "COMMUNITY SURVEY",
                                color = if (poll.isOfficialConsultation) CivicCyan else Color(0xFFF59E0B),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 1.sp
                            )
                            Text("Closes in ${poll.closesInDays}d", color = Color(0xFF64748B), fontSize = 11.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(poll.question, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(poll.description, color = Color(0xFF94A3B8), fontSize = 12.sp)

                        Spacer(modifier = Modifier.height(14.dp))

                        // Poll Options
                        val hasVoted = poll.selectedOptionId != null
                        poll.options.forEach { opt ->
                            val isSelectedByMe = poll.selectedOptionId == opt.id
                            val percent = if (poll.totalVotes > 0) (opt.votes.toFloat() / poll.totalVotes) else 0f
                            val animPercent by animateFloatAsState(
                                targetValue = if (hasVoted) percent else 0f,
                                animationSpec = tween(500),
                                label = "poll_pct"
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF0F1A3A))
                                    .border(1.dp, if (isSelectedByMe) CivicCyan else Color(0x2238BDF8), RoundedCornerShape(12.dp))
                                    .clickable(enabled = !hasVoted) { onVote(poll.id, opt.id) }
                            ) {
                                if (hasVoted) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(fraction = animPercent.coerceIn(0f, 1f))
                                            .background(
                                                if (isSelectedByMe) CivicRoyalBlue.copy(alpha = 0.45f)
                                                else Color(0x2638BDF8)
                                            )
                                    )
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                        if (isSelectedByMe) {
                                            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = CivicCyan, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(6.dp))
                                        }
                                        Text(opt.text, color = Color.White, fontSize = 13.sp)
                                    }
                                    if (hasVoted) {
                                        Text(
                                            text = "${(percent * 100).toInt()}% (${opt.votes})",
                                            color = CivicCyan,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "${poll.totalVotes} total verified citizen votes",
                            color = Color(0xFF64748B),
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}
