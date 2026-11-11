package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun AnimatedCityBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "city_ambient_transition")
    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "horizon_pulse"
    )

    val windowGlow by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "window_glow"
    )

    Box(modifier = modifier.fillMaxSize()) {
        // City Skyline Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // 1. Sky Gradient: Deep navy to midnight teal
            val skyBrush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF040817),
                    Color(0xFF070E22),
                    Color(0xFF0A1C3E),
                    Color(0xFF08264A)
                ),
                startY = 0f,
                endY = h * 0.85f
            )
            drawRect(brush = skyBrush, size = size)

            // 2. Horizon Ambient Cyan Glow
            val horizonY = h * 0.72f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF06B6D4).copy(alpha = 0.28f * pulse),
                        Color(0xFF2563EB).copy(alpha = 0.15f * pulse),
                        Color.Transparent
                    ),
                    center = Offset(w * 0.5f, horizonY),
                    radius = w * 0.75f * pulse
                ),
                radius = w * 0.75f * pulse,
                center = Offset(w * 0.5f, horizonY)
            )

            // 3. Distant Background Buildings (Layer 1 - Soft silhouettes)
            val layer1Buildings = listOf(
                Pair(0.02f, 0.22f) to 0.40f,
                Pair(0.14f, 0.18f) to 0.46f,
                Pair(0.24f, 0.26f) to 0.38f,
                Pair(0.42f, 0.20f) to 0.50f,
                Pair(0.55f, 0.25f) to 0.43f,
                Pair(0.72f, 0.18f) to 0.48f,
                Pair(0.84f, 0.22f) to 0.39f
            )
            val layer1Color = Color(0xFF0D1B3E)
            layer1Buildings.forEach { (xRange, heightRatio) ->
                val startX = xRange.first * w
                val bWidth = xRange.second * w
                val bHeight = heightRatio * h
                drawRect(
                    color = layer1Color,
                    topLeft = Offset(startX, horizonY - bHeight),
                    size = Size(bWidth, bHeight + (h - horizonY))
                )
            }

            // 4. Midground Buildings (Layer 2 - Illuminated outlines & windows)
            val layer2Buildings = listOf(
                Pair(0.06f, 0.14f) to 0.32f,
                Pair(0.18f, 0.16f) to 0.36f,
                Pair(0.32f, 0.13f) to 0.42f,
                Pair(0.44f, 0.17f) to 0.35f,
                Pair(0.60f, 0.15f) to 0.39f,
                Pair(0.74f, 0.14f) to 0.33f,
                Pair(0.87f, 0.16f) to 0.37f
            )
            val layer2Color = Color(0xFF09142E)
            val windowColor = Color(0xFF38BDF8).copy(alpha = 0.65f * windowGlow)
            val windowColorWarm = Color(0xFFFDE047).copy(alpha = 0.55f * windowGlow)

            layer2Buildings.forEach { (xRange, heightRatio) ->
                val startX = xRange.first * w
                val bWidth = xRange.second * w
                val bHeight = heightRatio * h
                val topY = horizonY - bHeight

                drawRect(
                    color = layer2Color,
                    topLeft = Offset(startX, topY),
                    size = Size(bWidth, bHeight + (h - horizonY))
                )

                // Glowing roofline edge
                drawLine(
                    color = Color(0xFF06B6D4).copy(alpha = 0.7f),
                    start = Offset(startX, topY),
                    end = Offset(startX + bWidth, topY),
                    strokeWidth = 2.5f
                )

                // Building spire/antenna for tall towers
                if (heightRatio > 0.38f) {
                    val spireX = startX + bWidth * 0.5f
                    drawLine(
                        color = Color(0xFF22D3EE),
                        start = Offset(spireX, topY),
                        end = Offset(spireX, topY - 26f),
                        strokeWidth = 2f
                    )
                    drawCircle(
                        color = Color(0xFFEF4444).copy(alpha = windowGlow),
                        radius = 3f,
                        center = Offset(spireX, topY - 26f)
                    )
                }

                // Grid of glowing windows
                val cols = 3
                val rows = 6
                val cellW = (bWidth - 16f) / cols
                val cellH = 14f
                for (r in 0 until rows) {
                    for (c in 0 until cols) {
                        val winX = startX + 8f + c * cellW
                        val winY = topY + 18f + r * (cellH + 8f)
                        if (winY < horizonY - 12f) {
                            val isWarm = (r + c) % 3 == 0
                            drawRect(
                                color = if (isWarm) windowColorWarm else windowColor,
                                topLeft = Offset(winX, winY),
                                size = Size(cellW * 0.65f, cellH * 0.7f)
                            )
                        }
                    }
                }
            }

            // 5. Foreground Base Ground Plane Gradient
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xCC070E22),
                        Color(0xFF040817)
                    ),
                    startY = horizonY - 20f,
                    endY = h
                ),
                topLeft = Offset(0f, horizonY - 20f),
                size = Size(w, h - horizonY + 20f)
            )

            // 6. Horizon Neon Pulse Line
            drawLine(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0x0006B6D4),
                        Color(0xFF06B6D4).copy(alpha = 0.8f),
                        Color(0xFF22D3EE),
                        Color(0xFF2563EB),
                        Color(0x002563EB)
                    )
                ),
                start = Offset(0f, horizonY),
                end = Offset(w, horizonY),
                strokeWidth = 2.5f
            )
        }

        // Floating particles layer
        FloatingParticles(particleCount = 20)

        // Foreground content
        content()
    }
}
