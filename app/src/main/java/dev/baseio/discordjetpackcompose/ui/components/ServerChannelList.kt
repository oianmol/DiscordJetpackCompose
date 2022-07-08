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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ServerChannelList(
    modifier: Modifier = Modifier,
    cornerSize: Dp = 8.dp,
    serverId: Int,
) {
    Surface(
        modifier = modifier.fillMaxHeight(),
        elevation = 4.dp,
        shape = RoundedCornerShape(topStart = cornerSize, topEnd = cornerSize)
    ) {
        AnimatedContent(targetState = serverId) { selectedScreenId ->
            when (selectedScreenId) {
                ServerIconSelector.FirstItemId -> DirectMessageList(
                    modifier = Modifier.fillMaxSize(),
                    openNewDMScreen = {},
                    openSearchScreen = {},
                )
                else -> ChannelList(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ServerChannelListPreview() {
    ServerChannelList(serverId = ServerIconSelector.FirstItemId)
}