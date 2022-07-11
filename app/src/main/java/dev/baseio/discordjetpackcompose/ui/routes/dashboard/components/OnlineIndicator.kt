package dev.baseio.discordjetpackcompose.ui.routes.dashboard.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider

@Composable
fun OnlineIndicator(
    modifier: Modifier = Modifier,
    isOnline: Boolean,
    indicatorSize: Dp = 12.dp,
) {
    val color by animateColorAsState(targetValue = if (isOnline) Color.Green else Color.Gray)
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

@Preview(showSystemUi = true)
@Composable
private fun OnlineIndicatorPreview() {
    OnlineIndicator(isOnline = true)
}