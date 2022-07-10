package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme

@Composable
fun CountIndicator(
    modifier: Modifier = Modifier,
    count: Int?,
    showCardBackground: Boolean = true,
    textSize: TextUnit = 8.sp
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = count != null && count > 0
    ) {
        val textSizeInDp = with(LocalDensity.current) { textSize.toDp() }
        Surface(
            shape = CircleShape,
            color = if (showCardBackground) DiscordColorProvider.colors.surface else Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(textSizeInDp * 2)
                    .clip(CircleShape)
                    .background(DiscordColorProvider.colors.error), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = count?.toString() ?: "0",
                    fontSize = textSize,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CountIndicatorPreview() {
    DiscordJetpackComposeTheme {
        CountIndicator(count = 100)
    }
}