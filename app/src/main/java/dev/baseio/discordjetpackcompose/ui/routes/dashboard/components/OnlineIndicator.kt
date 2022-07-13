package dev.baseio.discordjetpackcompose.ui.routes.dashboard.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme

@Composable
private fun OnlineIndicator(
    modifier: Modifier = Modifier,
    isOnline: Boolean,
    indicatorSize: Dp = 12.dp,
) {
    val color by animateColorAsState(targetValue = if (isOnline) Color(0xFF3da45c) else Color(0xFF6f7c8a))
    Surface(shape = CircleShape, color = DiscordColorProvider.colors.surface) {
        Canvas(
            modifier = modifier
                .padding(1.dp)
                .size(indicatorSize),
            onDraw = {
                drawCircle(
                    color = color,
                    radius = if (isOnline) size.width / 2.5f else size.width / 2.75f,
                    style = if (isOnline) Fill else Stroke(width = size.width.times(0.2f))
                )
            },
        )
    }
}

@Composable
fun OnlineIndicator(
    modifier: Modifier = Modifier,
    isOnline: Boolean,
    indicatorSize: Dp = 12.dp,
    indicatorAlignment: Alignment = Alignment.BottomEnd,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(contentAlignment = indicatorAlignment) {
        content()
        OnlineIndicator(
            modifier = modifier,
            isOnline = isOnline,
            indicatorSize = indicatorSize
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun OnlineIndicatorPreview() {
    DiscordJetpackComposeTheme {
        Column {
            OnlineIndicator(isOnline = true)
            OnlineIndicator(isOnline = true) {
                Surface(
                    modifier = Modifier.size(48.dp), shape = CircleShape, color = Color(0xFF3da45c)
                ) {}
            }
        }
    }
}