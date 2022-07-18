package dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverchannels

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import dev.baseio.discordjetpackcompose.repositories.ServerRepoImpl
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.channels.ChannelList
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.ServerIconSelector
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.directmessages.DirectMessageList
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.usecases.GetServerUseCase
import dev.baseio.discordjetpackcompose.viewmodels.HomeScreenViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ServerChannelList(
    modifier: Modifier = Modifier,
    serverId: String,
    chatUserList: List<ChatUserEntity>,
    homeScreenVM: HomeScreenViewModel = hiltViewModel(),
    openServerInfoBottomSheet: () -> Unit,
    onItemSelection: () -> Unit,
) {
    val serverState = homeScreenVM.currentServerEntity

    Surface(
        modifier = modifier.fillMaxHeight(),
        elevation = 4.dp,
        color = DiscordColorProvider.colors.background,
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
                    onInviteButtonClick = { serverId -> }, // TODO: Not implemented
                    openServerInfoBottomSheet = openServerInfoBottomSheet
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ServerChannelListPreview() {
    val vm = HomeScreenViewModel(GetServerUseCase(ServerRepoImpl()))
    LaunchedEffect(Unit) {
        vm.getServer("testId")
    }
    ServerChannelList(
        serverId = ServerIconSelector.DMScreenId,
        chatUserList = emptyList(),
        openServerInfoBottomSheet = {}
    ) {}
}