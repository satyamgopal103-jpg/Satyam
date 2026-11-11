package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CivicCyan
import com.example.ui.theme.CivicRoyalBlue
import com.example.ui.theme.CivicWhite

@Composable
fun CivicPulseLogo(
    modifier: Modifier = Modifier,
    size: Dp = 72.dp,
    showText: Boolean = false,
    textColor: Color = CivicWhite
) {
    val infiniteTransition = rememberInfiniteTransition(label = "logo_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val w = this.size.width
            val h = this.size.height
            val center = Offset(w / 2f, h / 2f)
            val radius = (w / 2f) * 0.88f

            // Outer glowing ring
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        CivicCyan,
                        CivicRoyalBlue,
                        Color(0xFF38BDF8),
                        CivicCyan
                    )
                ),
                radius = radius,
                center = center,
                style = Stroke(width = 3.5f)
            )

            // Inner soft halo
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        CivicCyan.copy(alpha = 0.22f * pulseScale),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius
                ),
                radius = radius,
                center = center
            )

            // Stylized City Buildings in lower half
            val buildingBaseY = center.y + radius * 0.7f
            val bCol = Color(0xFF38BDF8)
            val bFill = Color(0x661E3A8A)

            // 5 Building Rectangles
            val bSpecs = listOf(
                Triple(-0.6f, -0.36f, 0.45f),
                Triple(-0.35f, -0.12f, 0.65f),
                Triple(-0.1f, 0.1f, 0.85f),
                Triple(0.12f, 0.35f, 0.55f),
                Triple(0.36f, 0.6f, 0.38f)
            )

            bSpecs.forEach { (xStartRatio, xEndRatio, heightRatio) ->
                val bx = center.x + radius * xStartRatio
                val bw = radius * (xEndRatio - xStartRatio)
                val bh = radius * heightRatio
                val by = buildingBaseY - bh

                drawRect(
                    color = bFill,
                    topLeft = Offset(bx, by),
                    size = androidx.compose.ui.geometry.Size(bw, bh)
                )
                drawRect(
                    color = bCol,
                    topLeft = Offset(bx, by),
                    size = androidx.compose.ui.geometry.Size(bw, bh),
                    style = Stroke(width = 1.8f)
                )
            }

            // Civic Heartbeat / Pulse Line through the center
            val pulsePath = Path().apply {
                val py = center.y - 4f
                moveTo(center.x - radius * 0.75f, py)
                lineTo(center.x - radius * 0.35f, py)
                lineTo(center.x - radius * 0.2f, py - radius * 0.35f)
                lineTo(center.x, py + radius * 0.45f)
                lineTo(center.x + radius * 0.15f, py - radius * 0.65f)
                lineTo(center.x + radius * 0.3f, py)
                lineTo(center.x + radius * 0.75f, py)
            }

            drawPath(
                path = pulsePath,
                color = Color.White,
                style = Stroke(width = 3.5f, cap = StrokeCap.Round)
            )

            // Connected Civic Node (beacon dot)
            drawCircle(
                color = Color(0xFF22D3EE),
                radius = 4.5f * pulseScale,
                center = Offset(center.x + radius * 0.75f, center.y - 4f)
            )
        }

        if (showText) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "CIVIC PULSE",
                color = textColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 2.sp
            )
        }
    }
}
