package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.ServerIconSelector
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverchannels.ServerChannelList
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import dev.baseio.discordjetpackcompose.ui.utils.WindowInfo
import dev.baseio.discordjetpackcompose.ui.utils.rememberWindowInfo
import dev.baseio.discordjetpackcompose.utils.getSampleServer
import dev.baseio.discordjetpackcompose.viewmodels.DashboardScreenViewModel

@Composable
fun ServerDrawer(
    modifier: Modifier = Modifier,
    serverList: List<ServerEntity>,
    chatUserList: List<ChatUserEntity>,
    onAnyItemSelected: (Boolean, serverId: String) -> Unit,
    dashboardScreenVM: DashboardScreenViewModel = hiltViewModel(),
    onAddButtonClick: () -> Unit,
    openServerInfoBottomSheet: () -> Unit,
    viewModel: DashboardScreenViewModel
) {
    Row(
        modifier = modifier
    ) {
        val windowInfo = rememberWindowInfo()
        var currentSelectedServer by rememberSaveable { mutableStateOf(ServerIconSelector.DMScreenId) }
        var isAnyItemSelectedInCurrentServer: Boolean by remember { mutableStateOf(false) }

        LaunchedEffect(currentSelectedServer) {
            isAnyItemSelectedInCurrentServer = false
        }

        ServerIconSelector(
            modifier = Modifier.width(84.dp),
            serverList = serverList,
            onAddButtonClick = onAddButtonClick,
            currentSelectedItem = currentSelectedServer,
            onSelectedItemChanged = { updatedItem ->
                currentSelectedServer = updatedItem
                onAnyItemSelected(false, currentSelectedServer)
                dashboardScreenVM.getServer(currentSelectedServer)
            },
        )
        val widthFactor by animateFloatAsState(
            targetValue = if (!isAnyItemSelectedInCurrentServer) 1f
            else if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) 0.4f
            else 0.82f
        )
        ServerChannelList(
            modifier = Modifier.fillMaxWidth(widthFactor),
            serverId = currentSelectedServer,
            chatUserList = chatUserList,
            openServerInfoBottomSheet = openServerInfoBottomSheet,
            dashboardScreenVM = viewModel
        ) {
            isAnyItemSelectedInCurrentServer = true
            onAnyItemSelected(true, currentSelectedServer)
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun ServerDrawerPreview() {
    DiscordJetpackComposeTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ServerDrawer(onAddButtonClick = {},
                chatUserList = mutableListOf<ChatUserEntity>().apply {
                    repeat(20) {
                        add(
                            if (it % 2 == 0) {
                                ChatUserEntity(
                                    username = "testusername$it",
                                    name = "Test User",
                                    currentStatus = "Studying",
                                    isOnline = false,
                                )
                            } else {
                                ChatUserEntity(
                                    username = "testusername$it",
                                    isOnline = true,
                                )
                            }
                        )
                    }
                },
                serverList = listOf(
                    getSampleServer(serverId = "1"),
                    getSampleServer(serverId = "2"),
                ),
                onAnyItemSelected = { _, _ -> },
                openServerInfoBottomSheet = {},
                viewModel = hiltViewModel()
            )
        }
    }
}