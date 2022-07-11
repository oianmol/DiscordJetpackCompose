package dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.models.ServerInfoAction
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordSurface
import dev.baseio.discordjetpackcompose.ui.theme.ServerInfoTypography
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor
import dev.baseio.discordjetpackcompose.ui.utils.clickableWithRipple

@Composable
fun ServerInfoActions(
    modifier: Modifier = Modifier,
    actions: List<ServerInfoAction>,
    listShape: Shape = RoundedCornerShape(8.dp)
) {
    DiscordSurface(
        modifier = modifier.padding(horizontal = 16.dp).padding(top = 16.dp),
        shape = listShape,
        elevation = 1.dp,
        contentColor = DiscordColorProvider.colors.contentColorFor(DiscordColorProvider.colors.surface)
    ) {
        Column {
            actions.forEach { action ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableWithRipple(onClick = action.onClick)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.padding(vertical = 16.dp)) {
                        Text(
                            text = action.title,
                            style = ServerInfoTypography.h2,
                            color = action.titleColor
                        )
                        action.subtitle?.let { nnSubtitle ->
                            Text(
                                modifier = Modifier.padding(top = 4.dp),
                                text = nnSubtitle,
                                style = ServerInfoTypography.caption
                            )
                        }
                    }
                    action.trailingComposable()
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ServerInfoActionsPreview() {
    Column {
        ServerInfoActions(
            actions = listOf(
                ServerInfoAction(title = "Mark As Read",
                    titleColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                    subtitle = null,
                    trailingComposable = {},
                    onClick = {}),
            )
        )
        ServerInfoActions(
            actions = listOf(
                ServerInfoAction(title = "Edit Server Profile",
                    titleColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium),
                    subtitle = null,
                    trailingComposable = {},
                    onClick = {}),
                ServerInfoAction(title = "Direct Messages",
                    titleColor = MaterialTheme.colors.onSurface,
                    subtitle = "Allow direct messages from server members.",
                    trailingComposable = { Switch(checked = true, onCheckedChange = {}) },
                    onClick = {}),
                ServerInfoAction(title = "Hide Muted Channels",
                    titleColor = MaterialTheme.colors.onSurface,
                    subtitle = null,
                    trailingComposable = { Switch(checked = true, onCheckedChange = {}) },
                    onClick = {}),
                ServerInfoAction(title = "Leave Server",
                    titleColor = MaterialTheme.colors.error,
                    subtitle = null,
                    trailingComposable = {},
                    onClick = {}),
            )
        )
    }
}