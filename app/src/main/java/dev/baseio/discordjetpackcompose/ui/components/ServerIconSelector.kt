package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity

object ServerIconSelector {
    const val DMScreenId = "-1"
}

@Composable
fun ServerIconSelector(
    modifier: Modifier = Modifier,
    serverList: List<ServerEntity>,
    currentSelectedItem: String,
    onSelectedItemChanged: (String) -> Unit,
    onAddButtonClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .background(MaterialTheme.colors.surface)
            .padding(end = 4.dp, top = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ServerIconSelectorItem(
                id = ServerIconSelector.DMScreenId,
                iconId = R.drawable.ic_chat_bubble,
                isSelected = currentSelectedItem == ServerIconSelector.DMScreenId,
                onClick = { onSelectedItemChanged(ServerIconSelector.DMScreenId) },
            )
        }
        item {
            Divider(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(
                        top = 4.dp, bottom = 4.dp, start = ServerIconSelectorItem.iconStartPadding
                    ),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f),
            )
        }
        items(serverList.size) { index ->
            ServerIconSelectorItem(id = serverList[index].id,
                iconUri = serverList[index].thumbnailUri,
                isSelected = currentSelectedItem == serverList[index].id,
                unreadIndicatorCount = serverList[index].allChannelsUnreadCount,
                onClick = { onSelectedItemChanged(serverList[index].id) })
        }
        item {
            ServerIconSelectorItem(
                iconId = R.drawable.ic_add,
                isSelected = false,
                showIndicator = false,
                iconColor = Color.Green.copy(alpha = 0.5f), // todo: Use color from theme here
                onClick = onAddButtonClick
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ServerIconSelectorPreview() {
    ServerIconSelector(
        serverList = listOf(
            ServerEntity(
                id = "1",
                name = "Test Server 1",
                selectedAnimationUri = null,
                posterUri = null,
                channels = listOf(),
                hasNitroSubscription = true
            ), ServerEntity(
                id = "2",
                name = "Test Server 2",
                selectedAnimationUri = null,
                posterUri = null,
                channels = listOf(),
                hasNitroSubscription = false
            )
        ),
        onAddButtonClick = {},
        currentSelectedItem = ServerIconSelector.DMScreenId,
        onSelectedItemChanged = {}
    )
}