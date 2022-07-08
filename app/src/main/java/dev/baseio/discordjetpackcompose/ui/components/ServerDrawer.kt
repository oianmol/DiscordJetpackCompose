package dev.baseio.discordjetpackcompose.ui.components

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
        var currentSelectedItem by remember { mutableStateOf(ServerIconSelector.FirstItemId) }
        ServerIconSelector(
            modifier = Modifier.width(84.dp),
            serverList = listOf(
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
            ),
            onAddButtonClick = onAddButtonClick,
            currentSelectedItem = currentSelectedItem,
            onSelectedItemChanged = { updatedItem -> currentSelectedItem = updatedItem },
        )
        val widthFactor by animateFloatAsState(
            targetValue = when {
                currentSelectedItem == ServerIconSelector.FirstItemId -> 1f
                windowInfo.screenWidthInfo is WindowInfo.WindowType.Medium -> 0.4f
                else -> 0.82f
            }
        )
        ServerChannelList(
            modifier = Modifier.fillMaxWidth(widthFactor),
            serverId = currentSelectedItem,
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun ServerDrawerPreview() {
    DiscordJetpackComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ServerDrawer(onAddButtonClick = {})
        }
    }
}