package com.example.ui.screens.loading

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AnimatedCityBackground
import com.example.ui.components.CivicPulseLogo
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicWhite
import kotlinx.coroutines.delay

@Composable
fun DashboardLoadingScreen(
    onLoadingComplete: () -> Unit
) {
    val messages = listOf(
        "Connecting your community...",
        "Preparing local updates...",
        "Loading civic initiatives...",
        "Organizing your dashboard...",
        "Getting everything ready..."
    )

    var currentMsgIndex by remember { mutableIntStateOf(0) }
    var progress by remember { mutableFloatStateOf(0.15f) }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(500, easing = LinearEasing),
        label = "loading_progress"
    )

    LaunchedEffect(Unit) {
        for (i in messages.indices) {
            currentMsgIndex = i
            progress = (i + 1) / messages.size.toFloat()
            delay(550)
        }
        delay(300)
        onLoadingComplete()
    }

    AnimatedCityBackground {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CivicPulseLogo(size = 88.dp)

                Spacer(modifier = Modifier.height(28.dp))

                Text(
                    text = "PREPARING YOUR CIVIC SPACE",
                    color = CivicWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        progress = { animatedProgress },
                        color = CivicCyan,
                        trackColor = CivicCyan.copy(alpha = 0.2f),
                        strokeWidth = 3.5.dp,
                        modifier = Modifier.size(60.dp)
                    )
                    Text(
                        text = "${(animatedProgress * 100).toInt()}%",
                        color = CivicCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                AnimatedContent(
                    targetState = messages[currentMsgIndex],
                    transitionSpec = { fadeIn(tween(250)) togetherWith fadeOut(tween(250)) },
                    label = "msg_anim"
                ) { msg ->
                    Text(
                        text = msg,
                        color = androidx.compose.ui.graphics.Color(0xFFCBD5E1),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
