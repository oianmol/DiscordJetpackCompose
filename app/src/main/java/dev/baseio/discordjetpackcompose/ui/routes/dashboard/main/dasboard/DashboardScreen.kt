package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.dasboard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.entities.search.SearchSheetListItemEntity
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordRoute
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
import dev.baseio.discordjetpackcompose.ui.components.DiscordIcon
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.bottombar.DashboardBottomBar
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.bottombar.DashboardBottomBarItem
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.bottombar.DashboardBottomBarItemType
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.OnlineIndicator
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.SearchBottomSheet
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.ServerInfoBottomSheet
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.setupDashboardBottomNavScreens
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor
import dev.baseio.discordjetpackcompose.ui.utils.rememberCoilImageRequest
import dev.baseio.discordjetpackcompose.utils.Constants
import dev.baseio.discordjetpackcompose.viewmodels.DashboardScreenViewModel
import kotlinx.coroutines.launch

private object DashboardScreen {
    val iconSize = 24.dp
    val bottomBarIconModifier = Modifier.padding(16.dp)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    composeNavigator: ComposeNavigator,
    dashboardScreenVM: DashboardScreenViewModel = hiltViewModel(),
    serverList: List<ServerEntity> = dashboardScreenVM.serverList,
    searchSheetItemList: List<SearchSheetListItemEntity> = dashboardScreenVM.searchSheetItemList,
    userProfileImage: String = Constants.MMLogoUrl,
    totalUnreadCount: Int = serverList.sumOf { it.allChannelsUnreadCount }
) {
    val surfaceColor = DiscordColorProvider.colors.surface
    val contentColor = DiscordColorProvider.colors.contentColorFor(surfaceColor)

    var selectedBottomBarItem: DashboardBottomBarItemType by remember {
        mutableStateOf(DashboardBottomBarItemType.Home)
    }

    val searchSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val serverInfoSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val coroutineScope = rememberCoroutineScope()

    var selectedServerId: String? by remember { mutableStateOf(null) }
    var selectedChannelId: String? by remember { mutableStateOf(null) }

    val navController = rememberNavController()

    var isBottomBarDisplayed by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        dashboardScreenVM.apply {
            getSearchSheetItemList()
            getServerList()
        }
    }

    SearchBottomSheet(
        sheetState = searchSheetState,
        content = {
            ServerInfoBottomSheet(
                sheetState = serverInfoSheetState,
                onNotificationsIconClick = {
                    composeNavigator.navigate(DiscordScreen.NotificationSettings.name)
                },
                serverEntity = serverList.find { it.id == selectedServerId }) {
                Scaffold(
                    bottomBar = {
                        DashboardBottomBar(
                            bottomBarItems = listOf(
                                DashboardBottomBarItem(
                                    isSelected = selectedBottomBarItem == DashboardBottomBarItemType.Home,
                                    icon = {
                                        DiscordIcon(
                                            painter = painterResource(id = R.drawable.ic_discord_icon),
                                            contentDescription = null,
                                            modifier = DashboardScreen.bottomBarIconModifier,
                                        )
                                    },
                                    unreadCount = totalUnreadCount,
                                    onClick = {
                                        selectedBottomBarItem = DashboardBottomBarItemType.Home
                                        navController.navigateTab(DiscordScreen.Home)
                                    },
                                    type = DashboardBottomBarItemType.Home
                                ),
                                DashboardBottomBarItem(
                                    isSelected = selectedBottomBarItem == DashboardBottomBarItemType.Friends,
                                    icon = {
                                        DiscordIcon(
                                            imageVector = Icons.Default.EmojiPeople,
                                            contentDescription = null,
                                            modifier = DashboardScreen.bottomBarIconModifier,
                                        )
                                    },
                                    unreadCount = null,
                                    onClick = {
                                        selectedBottomBarItem = DashboardBottomBarItemType.Friends
                                        navController.navigateTab(DiscordScreen.Friends)
                                    },
                                    type = DashboardBottomBarItemType.Friends
                                ),
                                DashboardBottomBarItem(
                                    isSelected = selectedBottomBarItem == DashboardBottomBarItemType.Search,
                                    icon = {
                                        DiscordIcon(
                                            imageVector = Icons.Default.Search,
                                            contentDescription = null,
                                            modifier = DashboardScreen.bottomBarIconModifier,
                                        )
                                    },
                                    unreadCount = null,
                                    onClick = {
                                        coroutineScope.launch { searchSheetState.bottomSheetState.expand() }
                                    },
                                    type = DashboardBottomBarItemType.Search
                                ),
                                DashboardBottomBarItem(
                                    isSelected = selectedBottomBarItem == DashboardBottomBarItemType.Mentions,
                                    icon = {
                                        DiscordIcon(
                                            imageVector = Icons.Default.AlternateEmail,
                                            contentDescription = null,
                                            modifier = DashboardScreen.bottomBarIconModifier,
                                        )
                                    },
                                    unreadCount = 20,
                                    onClick = {
                                        selectedBottomBarItem = DashboardBottomBarItemType.Mentions
                                    },
                                    type = DashboardBottomBarItemType.Mentions
                                ),
                                DashboardBottomBarItem(
                                    isSelected = selectedBottomBarItem == DashboardBottomBarItemType.Profile,
                                    icon = {
                                        OnlineIndicator(isOnline = true) {
                                            AsyncImage(
                                                model = rememberCoilImageRequest(data = userProfileImage),
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .padding(2.dp)
                                                    .size(DashboardScreen.iconSize)
                                                    .clip(CircleShape)
                                            )
                                        }
                                    },
                                    unreadCount = null,
                                    onClick = {
                                        selectedBottomBarItem = DashboardBottomBarItemType.Profile
                                        navController.navigate(DiscordScreen.UserSettings.route)
                                    },
                                    type = DashboardBottomBarItemType.Profile
                                ),
                            ),
                            isDisplayed = isBottomBarDisplayed
                        )
                    },
                    backgroundColor = surfaceColor,
                    contentColor = contentColor
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = DiscordRoute.DashboardBottomNav.name,
                    ) {
                        setupDashboardBottomNavScreens(
                            composeNavigator = composeNavigator,
                            sheetState = serverInfoSheetState,
                            onSelectServer = { serverId ->
                                selectedServerId = serverId
                            },
                            shouldDisplayBottomBar = { isDisplayed ->
                                isBottomBarDisplayed = isDisplayed
                            }, serverList = serverList)
                    }
                }
            }
        },

        serverIdList = serverList.map { it.id },
        onServerSelect = { serverId -> selectedServerId = serverId },
        listItems = searchSheetItemList,
        onChannelSelect = { channelId -> selectedChannelId = channelId }
    )
}