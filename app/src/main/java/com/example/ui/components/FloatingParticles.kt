package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

data class Particle(
    val initialX: Float,
    val initialY: Float,
    val radius: Float,
    val speed: Float,
    val color: Color,
    val swayAmount: Float
)

@Composable
fun FloatingParticles(
    modifier: Modifier = Modifier,
    particleCount: Int = 22,
    baseColor: Color = Color(0xFF06B6D4)
) {
    val infiniteTransition = rememberInfiniteTransition(label = "particles_transition")
    val animProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "anim_progress"
    )

    val particles = remember {
        val rand = Random(42)
        List(particleCount) {
            val alpha = rand.nextFloat() * 0.35f + 0.12f
            Particle(
                initialX = rand.nextFloat(),
                initialY = rand.nextFloat(),
                radius = rand.nextFloat() * 7f + 3f,
                speed = rand.nextFloat() * 0.7f + 0.4f,
                color = when (rand.nextInt(3)) {
                    0 -> baseColor.copy(alpha = alpha)
                    1 -> Color(0xFF38BDF8).copy(alpha = alpha)
                    else -> Color(0xFF2563EB).copy(alpha = alpha * 0.8f)
                },
                swayAmount = rand.nextFloat() * 24f + 8f
            )
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        particles.forEach { p ->
            val currentYProgress = (p.initialY - animProgress * p.speed).let {
                if (it < 0f) it + 1f else it
            }
            val currentY = currentYProgress * canvasHeight
            val sway = kotlin.math.sin((animProgress * 2 * Math.PI + p.initialX * 10).toFloat()) * p.swayAmount
            val currentX = (p.initialX * canvasWidth + sway).coerceIn(0f, canvasWidth)

            drawCircle(
                color = p.color,
                radius = p.radius,
                center = Offset(currentX, currentY)
            )
        }
    }
}
