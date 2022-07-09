package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.entities.ChatUserEntity

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ServerChannelList(
    modifier: Modifier = Modifier,
    cornerSize: Dp = 8.dp,
    serverId: Int,
    chatUserList: List<ChatUserEntity>,
    onItemSelection: () -> Unit,
) {
    Surface(
        modifier = modifier.fillMaxHeight(),
        elevation = 4.dp,
        shape = RoundedCornerShape(topStart = cornerSize, topEnd = cornerSize)
    ) {
        AnimatedContent(targetState = serverId) { selectedScreenId ->
            when (selectedScreenId) {
                ServerIconSelector.DMScreenId -> DirectMessageList(
                    modifier = Modifier.fillMaxSize(),
                    openNewDMScreen = {} , // todo: Not implemented
                    openSearchScreen = {},
                    onItemSelection = onItemSelection,
                    chats = chatUserList
                )
                else -> ChannelList(
                    modifier = Modifier.fillMaxSize(),
                    onItemSelection = onItemSelection
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ServerChannelListPreview() {
    ServerChannelList(
        serverId = ServerIconSelector.DMScreenId,
        onItemSelection = {},
        chatUserList = emptyList()
    )
}