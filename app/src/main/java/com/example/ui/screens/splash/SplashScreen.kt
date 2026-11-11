package com.example.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var logoScale by remember { mutableStateOf(0.4f) }
    var textAlpha by remember { mutableStateOf(0f) }
    var taglineAlpha by remember { mutableStateOf(0f) }

    val animatedLogoScale by animateFloatAsState(
        targetValue = logoScale,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "logo_scale"
    )

    val animatedTextAlpha by animateFloatAsState(
        targetValue = textAlpha,
        animationSpec = tween(700, easing = LinearEasing),
        label = "text_alpha"
    )

    val animatedTaglineAlpha by animateFloatAsState(
        targetValue = taglineAlpha,
        animationSpec = tween(700, easing = LinearEasing),
        label = "tagline_alpha"
    )

    LaunchedEffect(Unit) {
        logoScale = 1.0f
        delay(400)
        textAlpha = 1.0f
        delay(400)
        taglineAlpha = 1.0f
        delay(1800)
        onSplashFinished()
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
                CivicPulseLogo(
                    size = 110.dp,
                    modifier = Modifier.scale(animatedLogoScale)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "CIVIC PULSE",
                    color = CivicWhite.copy(alpha = animatedTextAlpha),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 4.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Your Community. Your Voice. Your Future.",
                    color = CivicCyan.copy(alpha = animatedTaglineAlpha),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Together for a Better Tomorrow.",
                    color = Color(0xFFCBD5E1).copy(alpha = animatedTaglineAlpha),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "\"Every voice matters. Every community counts.\"",
                    color = Color(0xFF94A3B8).copy(alpha = animatedTaglineAlpha),
                    fontSize = 12.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(48.dp))

                CircularProgressIndicator(
                    color = CivicCyan.copy(alpha = animatedTaglineAlpha),
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}
