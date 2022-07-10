package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.repositories.ServerRepoImpl
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.utils.getSampleServerUIState
import dev.baseio.discordjetpackcompose.usecases.GetServerUseCase
import dev.baseio.discordjetpackcompose.utils.UIState
import dev.baseio.discordjetpackcompose.viewmodels.LandingScreenViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ServerChannelList(
    modifier: Modifier = Modifier,
    cornerSize: Dp = 8.dp,
    serverId: String,
    chatUserList: List<ChatUserEntity>,
    onItemSelection: () -> Unit,
) {
    var serverState: UIState<ServerEntity> by remember { mutableStateOf(UIState.Empty) }

    LaunchedEffect(serverId) {
        serverState = getSampleServerUIState(serverId = serverId) // TODO: Replace with actual implementation
    }

    Surface(
        modifier = modifier.fillMaxHeight(),
        elevation = 4.dp,
        color = DiscordColorProvider.colors.background,
        shape = RoundedCornerShape(topStart = cornerSize, topEnd = cornerSize)
    ) {
        AnimatedContent(
            targetState = serverId,
            transitionSpec = { fadeIn() with fadeOut() }
        ) { selectedScreenId ->
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
                    serverState = serverState,
                    onItemSelection = onItemSelection,
                    onInviteButtonClick = { serverId -> } // TODO: Not implemented
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ServerChannelListPreview() {
    val vm = LandingScreenViewModel(GetServerUseCase(ServerRepoImpl()))
    LaunchedEffect(Unit) {
        vm.getServer("testId")
    }
    ServerChannelList(
        serverId = ServerIconSelector.DMScreenId,
        onItemSelection = {},
        chatUserList = emptyList(),
    )
}