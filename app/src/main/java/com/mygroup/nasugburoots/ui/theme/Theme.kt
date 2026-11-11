
package com.mygroup.nasugburoots.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = DeepForest,
    surface = DeepForest,
    onPrimary = DeepForest,
    onSecondary = DeepForest,
    onTertiary = DeepForest,
    onBackground = Cream,
    onSurface = Cream
)

private val LightColorScheme = lightColorScheme(
    primary = Forest,
    secondary = Gold,
    tertiary = Muted,
    background = Cream,
    surface = Cream,
    onPrimary = Cream,
    onSecondary = DeepForest,
    onTertiary = Cream,
    onBackground = DeepForest,
    onSurface = DeepForest
)

@Composable
fun NasubuRootsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
