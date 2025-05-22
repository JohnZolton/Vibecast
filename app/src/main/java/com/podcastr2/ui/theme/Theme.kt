package com.podcastr2.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme // Keep for potential future light theme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.compose.ui.graphics.Color // Added to resolve Pink40, Purple40, PurpleGrey80, PurpleGrey40

// Define the custom dark color scheme using the colors from Color.kt
private val PodcastrDarkColorScheme = darkColorScheme(
    primary = DarkPurplePrimary,
    onPrimary = DarkPurpleOnPrimary,
    primaryContainer = DarkPurplePrimaryContainer,
    onPrimaryContainer = DarkPurpleOnPrimaryContainer,
    secondary = DarkPurpleSecondary,
    onSecondary = DarkPurpleOnSecondary,
    secondaryContainer = DarkPurpleSecondaryContainer,
    onSecondaryContainer = DarkPurpleOnSecondaryContainer,
    tertiary = DarkPurpleTertiary,
    onTertiary = DarkPurpleOnTertiary,
    tertiaryContainer = DarkPurpleTertiaryContainer,
    onTertiaryContainer = DarkPurpleOnTertiaryContainer,
    error = DarkPurpleError,
    onError = DarkPurpleOnError,
    errorContainer = DarkPurpleErrorContainer,
    onErrorContainer = DarkPurpleOnErrorContainer,
    background = DarkPurpleBackground,
    onBackground = DarkPurpleOnBackground,
    surface = DarkPurpleSurface,
    onSurface = DarkPurpleOnSurface,
    surfaceVariant = DarkPurpleSurfaceVariant,
    onSurfaceVariant = DarkPurpleOnSurfaceVariant,
    outline = DarkPurpleOutline,
    inverseOnSurface = DarkPurpleInverseOnSurface,
    inverseSurface = DarkPurpleInverseSurface,
    inversePrimary = DarkPurpleInversePrimary,
    surfaceTint = DarkPurpleSurfaceTint,
)

// It's good practice to also define a light theme, even if not immediately used.
// You can use the default lightColorScheme or define custom light colors.
private val PodcastrLightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    primaryContainer = PurpleGrey80, // Example, adjust as needed
    onPrimaryContainer = PurpleGrey40, // Example, adjust as needed
    secondary = Pink40,
    // ... other light theme colors, or use defaults
)

@Composable
fun PodcastrTheme(
    darkTheme: Boolean = true, // Default to dark theme as per request
    dynamicColor: Boolean = false, // Disable Material You to use custom dark purple theme
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> PodcastrDarkColorScheme
        else -> PodcastrLightColorScheme // Fallback to a defined light scheme if needed
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Set status bar to blend with the background in dark theme
            window.statusBarColor = colorScheme.background.toArgb()
            // Set navigation bar to match the background color
            window.navigationBarColor = colorScheme.background.toArgb()
            
            val insetsController = WindowCompat.getInsetsController(window, view)
            // Set status bar icons to light (for dark background)
            insetsController.isAppearanceLightStatusBars = !darkTheme
            // Set navigation bar icons to light (for dark background)
            insetsController.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        // typography = Typography, // Define Typography.kt if custom fonts/styles are needed
        // shapes = Shapes, // Define Shapes.kt if custom component shapes are needed
        content = content
    )
}
