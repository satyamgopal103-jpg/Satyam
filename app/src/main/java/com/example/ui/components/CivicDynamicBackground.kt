package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.ui.theme.BackgroundPreset
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun CivicDynamicBackground(
    backgroundPreset: BackgroundPreset = BackgroundPreset.ANIMATED_SKYLINE,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    when (backgroundPreset) {
        BackgroundPreset.ANIMATED_SKYLINE -> {
            AnimatedCityBackground(modifier = modifier, content = content)
        }
        BackgroundPreset.CYBER_GRID -> {
            CyberGridBackground(modifier = modifier, content = content)
        }
        BackgroundPreset.STARFIELD_AURORA -> {
            StarfieldAuroraBackground(modifier = modifier, content = content)
        }
        BackgroundPreset.GEOMETRIC_MESH -> {
            GeometricMeshBackground(modifier = modifier, content = content)
        }
        BackgroundPreset.MINIMAL_DEEP -> {
            MinimalDeepBackground(modifier = modifier, content = content)
        }
    }
}

@Composable
fun CyberGridBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "cyber_grid")
    val gridOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "grid_anim"
    )

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Deep cyber background
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF030712), Color(0xFF070E22), Color(0xFF0A193B))
                )
            )

            // Horizon Glow
            val horizonY = h * 0.55f
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x5506B6D4), Color(0x222563EB), Color.Transparent),
                    center = Offset(w * 0.5f, horizonY),
                    radius = w * 0.7f
                ),
                radius = w * 0.7f,
                center = Offset(w * 0.5f, horizonY)
            )

            // Perspective Grid Lines below horizon
            val gridColor = Color(0x3306B6D4)
            val vanishingPoint = Offset(w * 0.5f, horizonY)

            // Radiating perspective lines
            val numVLines = 14
            for (i in -numVLines..numVLines) {
                val bottomX = w * 0.5f + i * (w / (numVLines * 0.75f))
                drawLine(
                    color = gridColor,
                    start = vanishingPoint,
                    end = Offset(bottomX, h),
                    strokeWidth = 1.5f
                )
            }

            // Horizontal perspective lines moving towards viewer
            val numHLines = 9
            for (i in 0 until numHLines) {
                val normI = (i.toFloat() + gridOffset) / numHLines
                val y = horizonY + (h - horizonY) * (normI * normI)
                val alpha = (normI * 0.55f).coerceIn(0.1f, 0.6f)
                drawLine(
                    color = Color(0xFF06B6D4).copy(alpha = alpha),
                    start = Offset(0f, y),
                    end = Offset(w, y),
                    strokeWidth = (1.5f + normI * 2f)
                )
            }

            // Scanline horizontal laser
            val laserY = horizonY + (h - horizonY) * gridOffset
            drawLine(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.Transparent, Color(0xFF22D3EE), Color.White, Color(0xFF22D3EE), Color.Transparent)
                ),
                start = Offset(0f, laserY),
                end = Offset(w, laserY),
                strokeWidth = 2.5f
            )
        }

        FloatingParticles(particleCount = 18, baseColor = Color(0xFF00F0FF))
        content()
    }
}

@Composable
fun StarfieldAuroraBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "aurora")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(9000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "aurora_wave"
    )

    val stars = remember {
        val rand = Random(128)
        List(40) {
            Triple(rand.nextFloat(), rand.nextFloat(), rand.nextFloat() * 2f + 1f)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Cosmic sky gradient
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF02040D), Color(0xFF070B1E), Color(0xFF0D1B3E))
                )
            )

            // Twinkling stars
            stars.forEach { (xR, yR, r) ->
                val x = xR * w
                val y = yR * (h * 0.7f)
                val twinkle = (sin(waveOffset + xR * 10f) * 0.35f + 0.65f).toFloat()
                drawCircle(
                    color = Color.White.copy(alpha = twinkle),
                    radius = r * twinkle,
                    center = Offset(x, y)
                )
            }

            // Flowing Aurora wave 1 (Cyan/Teal)
            val path1 = Path().apply {
                moveTo(0f, h * 0.35f)
                val segments = 8
                for (i in 0..segments) {
                    val px = i * (w / segments)
                    val py = h * 0.35f + sin(waveOffset + i * 0.8f).toFloat() * 45f
                    lineTo(px, py)
                }
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(
                path = path1,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0x3306B6D4), Color(0x1810B981), Color.Transparent),
                    startY = h * 0.3f,
                    endY = h * 0.8f
                )
            )

            // Flowing Aurora wave 2 (Purple/Violet)
            val path2 = Path().apply {
                moveTo(0f, h * 0.45f)
                val segments = 8
                for (i in 0..segments) {
                    val px = i * (w / segments)
                    val py = h * 0.45f + sin(waveOffset * 0.7f + i * 0.9f).toFloat() * 35f
                    lineTo(px, py)
                }
                lineTo(w, h)
                lineTo(0f, h)
                close()
            }
            drawPath(
                path = path2,
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0x288B5CF6), Color(0x1538BDF8), Color.Transparent),
                    startY = h * 0.4f,
                    endY = h * 0.9f
                )
            )
        }

        FloatingParticles(particleCount = 16, baseColor = Color(0xFF38BDF8))
        content()
    }
}

@Composable
fun GeometricMeshBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mesh")
    val shift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mesh_shift"
    )

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Base slate
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF090D1A), Color(0xFF0D1730), Color(0xFF0A142A))
                )
            )

            // Floating luminous ambient spheres
            val sphere1X = w * (0.3f + 0.2f * shift)
            val sphere1Y = h * (0.25f + 0.1f * shift)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x442563EB), Color.Transparent),
                    center = Offset(sphere1X, sphere1Y),
                    radius = w * 0.6f
                ),
                radius = w * 0.6f,
                center = Offset(sphere1X, sphere1Y)
            )

            val sphere2X = w * (0.7f - 0.2f * shift)
            val sphere2Y = h * (0.7f - 0.15f * shift)
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x3806B6D4), Color.Transparent),
                    center = Offset(sphere2X, sphere2Y),
                    radius = w * 0.55f
                ),
                radius = w * 0.55f,
                center = Offset(sphere2X, sphere2Y)
            )

            // Geometric facet lattice lines
            val facetPoints = listOf(
                Offset(w * 0.1f, h * 0.2f),
                Offset(w * 0.5f, h * 0.15f),
                Offset(w * 0.9f, h * 0.25f),
                Offset(w * 0.75f, h * 0.5f),
                Offset(w * 0.25f, h * 0.55f),
                Offset(w * 0.5f, h * 0.8f),
                Offset(w * 0.85f, h * 0.85f)
            )

            for (i in 0 until facetPoints.size - 1) {
                drawLine(
                    color = Color(0x1F38BDF8),
                    start = facetPoints[i],
                    end = facetPoints[i + 1],
                    strokeWidth = 1f
                )
            }
        }

        FloatingParticles(particleCount = 14, baseColor = Color(0xFF38BDF8))
        content()
    }
}

@Composable
fun MinimalDeepBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRect(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF0F1E3E), Color(0xFF081024), Color(0xFF040814)),
                    center = Offset(size.width * 0.5f, size.height * 0.3f),
                    radius = size.width * 0.95f
                )
            )
        }
        content()
    }
}
