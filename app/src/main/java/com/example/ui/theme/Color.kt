package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// High Saturation Casino & Cockfight Color Palette
val CasinoBackground = Color(0xFF0C031A)
val CasinoSurface = Color(0xFF17072E)
val CasinoSurfaceElevated = Color(0xFF230D45)
val CasinoSurfaceHighlight = Color(0xFF331661)
val CasinoBorder = Color(0xFF4E228F)
val CasinoBorderGlow = Color(0xFF7B3FE4)

// Rooster Corners
val MeronRed = Color(0xFFFF0844)
val MeronRedBright = Color(0xFFFF2A6D)
val MeronRedDark = Color(0xFFB00020)
val MeronContainer = Color(0x33FF0844)

val WalaBlue = Color(0xFF00B4D8)
val WalaBlueBright = Color(0xFF00F0FF)
val WalaBlueDark = Color(0xFF0077B6)
val WalaContainer = Color(0x3300B4D8)

val BddGreen = Color(0xFF00E676)
val BddGreenBright = Color(0xFF69F0AE)
val BddContainer = Color(0x3300E676)

// Casino Gold & Neon Accents
val CasinoGold = Color(0xFFFFD700)
val CasinoGoldBright = Color(0xFFFFF176)
val CasinoGoldLight = Color(0xFFFFEE58)
val CasinoGoldDark = Color(0xFFFF8F00)
val CasinoOrange = Color(0xFFFF5722)
val NeonPink = Color(0xFFFF007F)
val NeonPurple = Color(0xFFB300FF)
val NeonCyan = Color(0xFF00F5D4)
val NeonEmerald = Color(0xFF00F59B)

// Chip Colors
val ChipWhite = Color(0xFFF5F5F7)
val ChipRed = Color(0xFFFF1744)
val ChipBlue = Color(0xFF2979FF)
val ChipGreen = Color(0xFF00E676)
val ChipBlack = Color(0xFF212121)
val ChipGold = Color(0xFFFFD700)
val ChipPurple = Color(0xFFAA00FF)

// Gradients
val MeronGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFF0844), Color(0xFFFF4E50))
)

val WalaGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF0072FF), Color(0xFF00C6FF))
)

val BddGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFF00B09B), Color(0xFF96C93D))
)

val GoldGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFFD700), Color(0xFFFF8F00))
)

val GoldMetallicGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFFE57F),
        Color(0xFFFFD700),
        Color(0xFFFF8F00),
        Color(0xFFFFD700)
    )
)

val CasinoHeaderGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF2B0957), Color(0xFF130429))
)

val SponsorGradient1 = Brush.linearGradient(
    colors = listOf(Color(0xFFFF007F), Color(0xFF7928CA), Color(0xFF2979FF))
)

val SponsorGradient2 = Brush.linearGradient(
    colors = listOf(Color(0xFFFF8F00), Color(0xFFFF0844), Color(0xFFB300FF))
)

val SponsorGradient3 = Brush.linearGradient(
    colors = listOf(Color(0xFF00C6FF), Color(0xFF0072FF), Color(0xFF7928CA))
)

val CardGlowBorder = Brush.sweepGradient(
    colors = listOf(
        Color(0xFFFFD700),
        Color(0xFFFF0844),
        Color(0xFF00F0FF),
        Color(0xFFFFD700)
    )
)
