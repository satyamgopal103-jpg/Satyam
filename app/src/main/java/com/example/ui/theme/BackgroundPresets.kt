package com.example.ui.theme

enum class BackgroundPreset(
    val title: String,
    val description: String,
    val iconEmoji: String
) {
    ANIMATED_SKYLINE(
        title = "Illuminated City Skyline",
        description = "Layered buildings, glowing windows, and cyan horizon pulse",
        iconEmoji = "🏙️"
    ),
    CYBER_GRID(
        title = "Cybernetic Digital Grid",
        description = "Futuristic glowing matrix perspective grid with animated node pulses",
        iconEmoji = "🌐"
    ),
    STARFIELD_AURORA(
        title = "Starfield & Aurora",
        description = "Deep celestial sky with shimmering stars and shifting aurora waves",
        iconEmoji = "✨"
    ),
    GEOMETRIC_MESH(
        title = "Geometric Ambient Mesh",
        description = "Soft glowing polygon shapes and floating gradient lighting",
        iconEmoji = "💎"
    ),
    MINIMAL_DEEP(
        title = "Minimalist Deep Slate",
        description = "Clean, distraction-free solid gradient backdrop",
        iconEmoji = "🌌"
    )
}
