package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CockfightColorScheme = darkColorScheme(
    primary = CasinoGold,
    onPrimary = Color(0xFF1F1000),
    primaryContainer = Color(0xFF4A3400),
    onPrimaryContainer = CasinoGoldBright,
    secondary = NeonPink,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF570028),
    onSecondaryContainer = Color(0xFFFFD8E4),
    tertiary = WalaBlueBright,
    onTertiary = Color(0xFF001F29),
    tertiaryContainer = Color(0xFF004D63),
    onTertiaryContainer = Color(0xFFBBE9FF),
    background = CasinoBackground,
    onBackground = Color(0xFFF0E8FF),
    surface = CasinoSurface,
    onSurface = Color(0xFFF0E8FF),
    surfaceVariant = CasinoSurfaceElevated,
    onSurfaceVariant = Color(0xFFD6C7EB),
    outline = CasinoBorder,
    outlineVariant = CasinoBorderGlow,
    error = MeronRedBright,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    // Always use our saturated casino theme instead of plain dynamic system gray
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = CockfightColorScheme,
        typography = Typography,
        content = content
    )
}
