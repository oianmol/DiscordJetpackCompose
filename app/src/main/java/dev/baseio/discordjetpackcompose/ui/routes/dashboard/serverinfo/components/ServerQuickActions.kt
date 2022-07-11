package dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.models.ServerQuickAction
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.ServerInfoTypography
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor

@Composable
fun ServerQuickActions(
    modifier: Modifier = Modifier, actions: List<ServerQuickAction>
) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        actions.forEach { action ->
            TextButton(onClick = action.onClick, modifier = Modifier.weight(1f), colors = ButtonDefaults.textButtonColors(
                contentColor = DiscordColorProvider.colors.contentColorFor(DiscordColorProvider.colors.primary)
            )) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = action.icon),
                        contentDescription = null,
                        tint = action.iconTint
                            ?: DiscordColorProvider.colors.onSurface.copy(alpha = 0.5f)
                    )
                    Text(
                        text = action.label,
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = ServerInfoTypography.caption.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ServerQuickActionsPreview() {
    ServerQuickActions(
        actions = listOf(
            ServerQuickAction(
                icon = R.drawable.ic_boost,
                iconTint = Color.Magenta,
                label = "3 Boosts",
                onClick = {},
            ),
            ServerQuickAction(
                icon = R.drawable.ic_notifications,
                label = stringResource(R.string.server_info_notifications_btn_txt),
                onClick = {},
            ),
            ServerQuickAction(
                icon = R.drawable.ic_invite,
                label = stringResource(R.string.server_info_invite_btn_txt),
                onClick = {},
            ),
        )
    )
}