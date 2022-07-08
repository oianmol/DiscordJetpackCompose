package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.utils.WindowInfo
import dev.baseio.discordjetpackcompose.ui.utils.rememberWindowInfo

@Composable
fun ServerDrawer(
    onAddButtonClick: () -> Unit
) {
    Row {
        val windowInfo = rememberWindowInfo()
        ServerIconSelector(
            modifier = Modifier.width(84.dp), serverList = listOf(
                ServerEntity(
                    id = "1",
                    name = "Test Server 1",
                    selectedAnimationUri = null,
                    posterUri = null,
                    channels = listOf()
                ), ServerEntity(
                    id = "2",
                    name = "Test Server 2",
                    selectedAnimationUri = null,
                    posterUri = null,
                    channels = listOf()
                )
            ), onAddButtonClick = onAddButtonClick
        )
        ServerChannelList(
            modifier = Modifier.fillMaxWidth(if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium) 0.4f else 0.82f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ServerDrawerPreview() {
    DiscordJetpackComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ServerDrawer(onAddButtonClick = {})
        }
    }
}