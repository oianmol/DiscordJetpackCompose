package dev.baseio.discordjetpackcompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette @Composable get() = darkColors(
    primary = design_default_color_primary_dark,
    primaryVariant = design_dark_default_color_primary_variant,
    secondary = design_dark_default_color_secondary,
    secondaryVariant = design_dark_default_color_secondary_variant,
    background = design_dark_default_color_background,
    surface = design_dark_default_color_surface,
    error = design_dark_default_color_error,
    onPrimary = design_default_color_on_primary,
    onSecondary = design_dark_default_color_on_secondary,
    onBackground = design_dark_default_color_on_background,
    onSurface = design_dark_default_color_on_surface,
    onError = design_dark_default_color_on_error
)

private val LightColorPalette @Composable get() = lightColors(
    primary = design_default_color_primary,
    primaryVariant = design_default_color_primary_variant,
    secondary = design_default_color_secondary,
    secondaryVariant = design_default_color_secondary_variant,
    background = design_default_color_background,
    surface = design_default_color_surface,
    error = design_default_color_error,
    onPrimary = design_dark_default_color_on_primary,
    onSecondary = design_default_color_on_secondary,
    onBackground = design_default_color_on_background,
    onSurface = design_default_color_on_surface,
    onError = design_default_color_on_error
)

@Composable
fun DiscordJetpackComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
