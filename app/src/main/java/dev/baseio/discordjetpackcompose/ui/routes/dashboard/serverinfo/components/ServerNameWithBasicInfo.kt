package dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.ServerInfoTypography
import dev.baseio.discordjetpackcompose.ui.theme.alerter_default_success_background

private const val DividerAlpha = 0.12f

@Composable
fun ServerNameWithBasicInfo(
    modifier: Modifier = Modifier,
    serverName: String,
    serverDescription: String? = null,
    onlineMembersCount: Int,
    totalMembersCount: Int,
) {
    Column {
        Column(
            modifier = modifier
        ) {
            Text(
                text = serverName,
                style = ServerInfoTypography.h1,
                modifier = Modifier.padding(top = 24.dp)
            )
            serverDescription?.let { nnServerDescription ->
                Text(
                    text = nnServerDescription,
                    style = ServerInfoTypography.subtitle1,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusCircle(
                    modifier = Modifier.padding(end = 8.dp),
                    color = alerter_default_success_background
                )
                Text(
                    text = stringResource(
                        R.string.server_desc_online_members_count, onlineMembersCount.toString()
                    ), style = ServerInfoTypography.subtitle2
                )
                Spacer(modifier = Modifier.padding(start = 16.dp))
                StatusCircle(modifier = Modifier.padding(end = 8.dp), color = Color.Gray)
                Text(
                    text = stringResource(
                        id = R.string.server_desc_total_members_count,
                        totalMembersCount
                    ),
                    style = ServerInfoTypography.subtitle2
                )
            }
        }
        Divider(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            color = DiscordColorProvider.colors.onSurface.copy(alpha = DividerAlpha)
        )
    }
}

@Composable
private fun StatusCircle(modifier: Modifier = Modifier, color: Color, size: Dp = 8.dp) {
    Canvas(modifier = modifier.size(size), onDraw = {
        drawCircle(color = color)
    })
}

@Preview(showSystemUi = true)
@Composable
private fun ServerNameWithBasicInfoPreview() {
    ServerNameWithBasicInfo(
        modifier = Modifier.padding(horizontal = 16.dp),
        serverName = "Coding in Flow",
        serverDescription = "The best place for programmers to learn and find new friends.",
        onlineMembersCount = 409,
        totalMembersCount = 6715
    )
}