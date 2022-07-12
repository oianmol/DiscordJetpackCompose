package dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.theme.DiscordSurface

enum class DiscordIndicatorPosition {
    Start, End, Top, Bottom
}

@Composable
fun DiscordIndicator(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    indicatorPosition: DiscordIndicatorPosition = DiscordIndicatorPosition.Start,
    indicatorColor: Color = DiscordColorProvider.colors.onSurface,
    indicatorSize: Dp = 8.dp,
    includeInvisibleIndicatorPadding: Boolean = false,
    content: @Composable () -> Unit
) {
    val indicatorAlignment by remember {
        mutableStateOf(
            when (indicatorPosition) {
                DiscordIndicatorPosition.Start -> Alignment.CenterStart
                DiscordIndicatorPosition.Top -> Alignment.TopCenter
                DiscordIndicatorPosition.End -> Alignment.CenterEnd
                DiscordIndicatorPosition.Bottom -> Alignment.BottomCenter
            }
        )
    }
    val indicatorShape by remember(indicatorAlignment) {
        mutableStateOf(
            when (indicatorPosition) {
                DiscordIndicatorPosition.Start -> RoundedCornerShape(
                    topEndPercent = 50,
                    bottomEndPercent = 50
                )
                DiscordIndicatorPosition.Top -> RoundedCornerShape(
                    bottomStartPercent = 50,
                    bottomEndPercent = 50
                )
                DiscordIndicatorPosition.End -> RoundedCornerShape(
                    topStartPercent = 50,
                    bottomStartPercent = 50
                )
                DiscordIndicatorPosition.Bottom -> RoundedCornerShape(
                    topStartPercent = 50,
                    topEndPercent = 50
                )
            }
        )
    }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier.padding(
                start = if (indicatorPosition == DiscordIndicatorPosition.Start && (isEnabled || includeInvisibleIndicatorPadding)) indicatorSize else 0.dp,
                top = if (indicatorPosition == DiscordIndicatorPosition.Top && (isEnabled || includeInvisibleIndicatorPadding)) indicatorSize else 0.dp,
                end = if (indicatorPosition == DiscordIndicatorPosition.End && (isEnabled || includeInvisibleIndicatorPadding)) indicatorSize else 0.dp,
                bottom = if (indicatorPosition == DiscordIndicatorPosition.Bottom && (isEnabled || includeInvisibleIndicatorPadding)) indicatorSize else 0.dp,
            ),
        ) { content() }
        if (isEnabled) {
            DiscordSurface(
                modifier = Modifier
                    .size(
                        width = when (indicatorPosition) {
                            DiscordIndicatorPosition.Start, DiscordIndicatorPosition.End -> indicatorSize.times(0.7f)
                            DiscordIndicatorPosition.Top, DiscordIndicatorPosition.Bottom -> indicatorSize
                        },
                        height = when (indicatorPosition) {
                            DiscordIndicatorPosition.Start, DiscordIndicatorPosition.End -> indicatorSize
                            DiscordIndicatorPosition.Top, DiscordIndicatorPosition.Bottom -> indicatorSize.times(0.7f)
                        },
                    )
                    .align(indicatorAlignment),
                shape = indicatorShape,
                color = indicatorColor
            ) {}
        }
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DiscordIndicatorPreview() {
    DiscordJetpackComposeTheme {
        Column {
            DiscordIndicator(
                modifier = Modifier.padding(bottom = 32.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_discord_icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    Column {
                        Text(text = "Test Title")
                        Text(text = "Test Subtitle", modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
            DiscordIndicator(
                modifier = Modifier.padding(bottom = 32.dp),
                indicatorPosition = DiscordIndicatorPosition.End
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_discord_icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    Column {
                        Text(text = "Test Title")
                        Text(text = "Test Subtitle", modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
            DiscordIndicator(
                modifier = Modifier.padding(bottom = 32.dp),
                isEnabled = false
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_discord_icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                    )
                    Spacer(modifier = Modifier.padding(start = 16.dp))
                    Column {
                        Text(text = "Test Title")
                        Text(text = "Test Subtitle", modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}