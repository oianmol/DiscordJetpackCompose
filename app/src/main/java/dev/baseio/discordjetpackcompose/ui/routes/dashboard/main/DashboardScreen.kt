package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.google.accompanist.insets.ui.Scaffold
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordRoute
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
import dev.baseio.discordjetpackcompose.ui.components.DiscordIcon
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.bottombar.DashboardBottomBar
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.bottombar.DashboardBottomBarItem
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.bottombar.DashboardBottomBarItemType
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.OnlineIndicator
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.dashboardBottomNavScreens
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.SearchBottomSheet
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.ServerInfoBottomSheet
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor
import dev.baseio.discordjetpackcompose.ui.utils.getSampleServerList
import dev.baseio.discordjetpackcompose.ui.utils.getSampleSheetListItems
import dev.baseio.discordjetpackcompose.ui.utils.rememberCoilImageRequest
import dev.baseio.discordjetpackcompose.utils.Constants
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    composeNavigator: ComposeNavigator
) {
    val surfaceColor = DiscordColorProvider.colors.surface
    val contentColor = DiscordColorProvider.colors.contentColorFor(surfaceColor)
    var currentSelectedItem: DashboardBottomBarItemType by remember {
        mutableStateOf(DashboardBottomBarItemType.Home)
    }
    val userProfileImage = remember { Constants.MMLogoUrl }
    val iconSize = remember { 24.dp }
    val commonModifier = remember { Modifier.padding(16.dp) }

    val searchSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    var selectedServerId: String? by remember { mutableStateOf(null) }
    var selectedChannelId: String? by remember { mutableStateOf(null) }

    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        composeNavigator.handleNavigationCommands(navController)
    }

    SearchBottomSheet(
        sheetState = searchSheetState,
        content = {
            val serverList = remember { getSampleServerList() }
            val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
            ServerInfoBottomSheet(
                sheetState = sheetState, serverEntity = serverList.find { it.id == selectedServerId }) {
                Scaffold(
                    bottomBar = {
                        DashboardBottomBar(
                            bottomBarItems = listOf(
                                DashboardBottomBarItem(
                                    isSelected = currentSelectedItem == DashboardBottomBarItemType.Home,
                                    icon = {
                                        DiscordIcon(
                                            painter = painterResource(id = R.drawable.ic_discord_icon),
                                            contentDescription = null,
                                            modifier = commonModifier,
                                        )
                                    },
                                    unreadCount = 242,
                                    onClick = {
                                        currentSelectedItem = DashboardBottomBarItemType.Home
                                        navController.navigate(DiscordScreen.Home.route)
                                    },
                                    type = DashboardBottomBarItemType.Home
                                ),
                                DashboardBottomBarItem(
                                    isSelected = currentSelectedItem == DashboardBottomBarItemType.Friends,
                                    icon = {
                                        DiscordIcon(
                                            imageVector = Icons.Default.EmojiPeople,
                                            contentDescription = null,
                                            modifier = commonModifier,
                                        )
                                    },
                                    unreadCount = null,
                                    onClick = {
                                        currentSelectedItem = DashboardBottomBarItemType.Friends
                                        navController.navigate(DiscordScreen.Friends.route)
                                    },
                                    type = DashboardBottomBarItemType.Friends
                                ),
                                DashboardBottomBarItem(
                                    isSelected = currentSelectedItem == DashboardBottomBarItemType.Search,
                                    icon = {
                                        DiscordIcon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = null,
                                            modifier = commonModifier,
                                        )
                                    },
                                    unreadCount = null,
                                    onClick = {
                                        coroutineScope.launch { searchSheetState.bottomSheetState.expand() }
                                    },
                                    type = DashboardBottomBarItemType.Search
                                ),
                                DashboardBottomBarItem(
                                    isSelected = currentSelectedItem == DashboardBottomBarItemType.Mentions,
                                    icon = {
                                        DiscordIcon(
                                            imageVector = Icons.Default.AlternateEmail,
                                            contentDescription = null,
                                            modifier = commonModifier,
                                        )
                                    },
                                    unreadCount = 20,
                                    onClick = {
                                        currentSelectedItem = DashboardBottomBarItemType.Mentions
                                    },
                                    type = DashboardBottomBarItemType.Mentions
                                ),
                                DashboardBottomBarItem(
                                    isSelected = currentSelectedItem == DashboardBottomBarItemType.Profile,
                                    icon = {
                                        OnlineIndicator(isOnline = true) {
                                            AsyncImage(
                                                model = rememberCoilImageRequest(data = userProfileImage),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .padding(2.dp)
                                                    .size(iconSize)
                                                    .clip(CircleShape)
                                            )
                                        }
                                    },
                                    unreadCount = null,
                                    onClick = {
                                        currentSelectedItem = DashboardBottomBarItemType.Profile
                                    },
                                    type = DashboardBottomBarItemType.Profile
                                ),
                            )
                        )
                    },
                    backgroundColor = surfaceColor,
                    contentColor = contentColor
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = DiscordRoute.DashboardBottomNav.name,
                    ) {
                        dashboardBottomNavScreens(composeNavigator = composeNavigator, sheetState = sheetState, onSelectServer = { serverId ->
                            selectedServerId = serverId
                        })
                    }
                }
            }
        },
        serverIdList = listOf("1", "2", "3", "4", "5"),
        onServerSelect = { serverId -> selectedServerId = serverId },
        listItems = getSampleSheetListItems(),
        onChannelSelect = { channelId -> selectedChannelId = channelId }
    )
}