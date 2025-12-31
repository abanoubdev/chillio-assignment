package net.chillio.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightNetflixLikeColorScheme = lightColorScheme(
    primary = ChiliPepperRed,
    onPrimary = Color.White,
    primaryContainer = ChiliPepperRedLight,
    onPrimaryContainer = LightOn,

    secondary = WarmAccent,
    onSecondary = Color(0xFF1A1200),
    secondaryContainer = WarmAccentLight,
    onSecondaryContainer = LightOn,

    tertiary = CoolAccent,
    onTertiary = Color.White,
    tertiaryContainer = CoolAccentLight,
    onTertiaryContainer = LightOn,

    error = Error,
    onError = OnError,
    errorContainer = ErrorContainerLight,
    onErrorContainer = OnErrorContainerLight,

    background = LightBg,
    onBackground = LightOn,
    surface = LightSurface,
    onSurface = LightOn,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnVariant,

    inverseSurface = DarkSurface,
    inverseOnSurface = DarkOn,

    outline = LightOutline,
)

val DarkNetflixLikeColorScheme = darkColorScheme(
    primary = ChiliPepperRed,
    onPrimary = Color.White,
    primaryContainer = ChiliPepperRedDark,
    onPrimaryContainer = DarkOn,

    secondary = WarmAccent,
    onSecondary = Color(0xFF1A1200),
    secondaryContainer = WarmAccentDark,
    onSecondaryContainer = DarkOn,

    tertiary = CoolAccent,
    onTertiary = Color.White,
    tertiaryContainer = CoolAccentDark,
    onTertiaryContainer = DarkOn,

    error = Error,
    onError = OnError,
    errorContainer = ErrorContainerDark,
    onErrorContainer = OnErrorContainerDark,

    background = DarkBg,
    onBackground = DarkOn,
    surface = DarkSurface,
    onSurface = DarkOn,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnVariant,

    inverseSurface = Color(0xFFEDEDF2),
    inverseOnSurface = DarkBg,

    outline = DarkOutline,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val scheme = if (darkTheme) DarkNetflixLikeColorScheme else LightNetflixLikeColorScheme
    MaterialTheme(
        colorScheme = scheme,
        content = content,
        typography = Typo
    )
}