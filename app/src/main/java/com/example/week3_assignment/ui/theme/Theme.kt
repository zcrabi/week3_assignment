package com.example.week3_assignment.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Toz pembe & beyaz renk paleti
private val DustyPinkColors = lightColorScheme(
    primary = Color(0xFFC2798C),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF8E1E7),
    onPrimaryContainer = Color(0xFF5A2A38),
    secondary = Color(0xFFB38A95),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF5E6EA),
    onSecondaryContainer = Color(0xFF5A2A38),
    background = Color(0xFFFFF9FA),
    onBackground = Color(0xFF3B2A2F),
    surface = Color.White,
    onSurface = Color(0xFF3B2A2F),
    surfaceVariant = Color(0xFFFCEFF2),
    onSurfaceVariant = Color(0xFF8A6B73),
    surfaceContainerHighest = Color(0xFFFCEFF2),
    surfaceContainerHigh = Color(0xFFFDF3F5),
    surfaceContainer = Color(0xFFFDF5F7),
    surfaceContainerLow = Color(0xFFFFF7F9),
    surfaceContainerLowest = Color.White,
    outline = Color(0xFFDDB8C2),
    outlineVariant = Color(0xFFF1DCE2),
    error = Color(0xFFC24E5C),
    onError = Color.White,
    errorContainer = Color(0xFFFBE0E3),
    onErrorContainer = Color(0xFF6B1E28)
)

// Telefon koyu modda da olsa uygulama hep toz pembe/beyaz görünsün diye
// dinamik renk ve koyu tema kapalı
@Composable
fun Week3_assignmentTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DustyPinkColors,
        content = content
    )
}