package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen.ChannelMemberScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen.ChatScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.dasboard.getFakeChatUserList
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.dasboard.getFakeServerList
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.channel_member_bg
import dev.baseio.discordjetpackcompose.viewmodels.DashboardScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

private enum class DrawerTypes {
    LEFT, RIGHT
}

enum class CenterScreenState {
    LEFT_ANCHORED, CENTER, RIGHT_ANCHORED
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    serverList: List<ServerEntity> = getFakeServerList(),
    chatUserList: List<ChatUserEntity> = getFakeChatUserList(),
    viewModel: DashboardScreenViewModel = hiltViewModel(),
    shouldDisplayBottomBar: (Boolean) -> Unit,
    composeNavigator: ComposeNavigator,
    onSelectServer: (String) -> Unit,
    sheetState: ModalBottomSheetState
) {

    val sysUiController = rememberSystemUiController()
    val colors = DiscordColorProvider.colors
    SideEffect {
        sysUiController.setSystemBarsColor(color = colors.background)
        sysUiController.setNavigationBarColor(color = colors.background)
    }

    val density = LocalDensity.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val screenCenterDp by remember(screenWidth) { mutableStateOf(screenWidth.times(0.5f)) }
    val leftEndpointDp by remember(screenWidth) { mutableStateOf(screenWidth.times(-0.4f)) }
    val rightEndpointDp by remember(screenWidth) { mutableStateOf(screenWidth.times(1.4f)) }

    val screenCenterPx by remember(screenCenterDp) { mutableStateOf(with(density) { screenCenterDp.toPx() }) }
    val leftEndpointPx by remember(leftEndpointDp) { mutableStateOf(with(density) { leftEndpointDp.toPx() }) }
    val rightEndpointPx by remember(rightEndpointDp) { mutableStateOf(with(density) { rightEndpointDp.toPx() }) }

    val swipeableState = rememberSwipeableState(initialValue = CenterScreenState.RIGHT_ANCHORED)
    val anchors = mapOf(
        leftEndpointPx to CenterScreenState.LEFT_ANCHORED,
        screenCenterPx to CenterScreenState.CENTER,
        rightEndpointPx to CenterScreenState.RIGHT_ANCHORED
    )

    val drawerOnTop by remember {
        derivedStateOf {
            when (swipeableState.currentValue) {
                CenterScreenState.LEFT_ANCHORED -> DrawerTypes.RIGHT
                CenterScreenState.CENTER -> {
                    if (swipeableState.direction < 0) DrawerTypes.RIGHT
                    else DrawerTypes.LEFT
                }
                CenterScreenState.RIGHT_ANCHORED -> DrawerTypes.LEFT
            }
        }
    }

    var isAnyItemSelectedInServers: Boolean by remember { mutableStateOf(false) }

    val swipeableModifier by remember(isAnyItemSelectedInServers) {
        mutableStateOf(
            if (isAnyItemSelectedInServers) {
                Modifier.swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal
                )
            } else {
                Modifier
            }
        )
    }

    val leftDrawerModifier by remember(drawerOnTop, isAnyItemSelectedInServers) {
        mutableStateOf(
            swipeableModifier
                .zIndex(if (drawerOnTop == DrawerTypes.LEFT) 1f else 0f)
                .alpha(if (drawerOnTop == DrawerTypes.LEFT) 1f else 0f)
        )
    }

    val rightDrawerModifier by remember(drawerOnTop, isAnyItemSelectedInServers) {
        mutableStateOf(
            swipeableModifier
                .zIndex(if (drawerOnTop == DrawerTypes.RIGHT) 1f else 0f)
                .alpha(if (drawerOnTop == DrawerTypes.RIGHT) 1f else 0f)
        )
    }

    val centerScreenOffset by remember {
        derivedStateOf { (swipeableState.offset.value - screenCenterPx).roundToInt() }
    }

    val systemUiController = rememberSystemUiController()
    val isSystemInDarkTheme = isSystemInDarkTheme()

    SideEffect {
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = !isSystemInDarkTheme
        )
    }

    val displayBottomBar by remember {
        derivedStateOf {
            swipeableState.direction >= 0 && swipeableState.currentValue == CenterScreenState.RIGHT_ANCHORED
        }
    }

    LaunchedEffect(displayBottomBar) {
        shouldDisplayBottomBar(displayBottomBar)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        val coroutineScope = rememberCoroutineScope()

        ServerDrawer(
            modifier = leftDrawerModifier,
            serverList = serverList,
            chatUserList = chatUserList,
            onAnyItemSelected = { isSelected, currentServerId ->
                isAnyItemSelectedInServers = isSelected
                if (isSelected) {
                    coroutineScope.launch { swipeableState.animateTo(CenterScreenState.CENTER) }
                }
                onSelectServer(currentServerId)
            },
            onAddButtonClick = { composeNavigator.navigate(DiscordScreen.CreateServer.name) },
            openServerInfoBottomSheet = { coroutineScope.launch { sheetState.show() } },
            viewModel = viewModel
        )
        ChannelMemberScreen(
            modifier = rightDrawerModifier
                .fillMaxHeight()
                .fillMaxWidth(0.85f)
                .background(channel_member_bg)
                .align(Alignment.CenterEnd),
            onInviteButtonClicked = {
                composeNavigator.navigate(DiscordScreen.Invite.route)
            }
        )

        val centerScreenZIndex by remember {
            derivedStateOf {
                if (swipeableState.isAnimationRunning || swipeableState.currentValue == CenterScreenState.CENTER || swipeableState.progress.fraction in 0.05f..0.95f) 1f
                else 0.5f
            }
        }
        AnimatedVisibility(
            modifier = swipeableModifier
                .zIndex(centerScreenZIndex)
                .offset { IntOffset(x = centerScreenOffset, y = 0) },
            visible = isAnyItemSelectedInServers,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            val shouldNotFocusCenterScreen by remember {
                derivedStateOf {
                    !swipeableState.isAnimationRunning &&
                            swipeableState.progress.fraction !in 0.05f..0.95f &&
                            swipeableState.currentValue != CenterScreenState.CENTER
                }
            }
            val focusOpacity by animateFloatAsState(targetValue = if (shouldNotFocusCenterScreen) ContentAlpha.disabled else 0f)
            ChatScreen(
              composeNavigator = composeNavigator,
              focusOpacity = focusOpacity,
              userName = viewModel.currentSelectedChatUsername.collectAsState(),
              isOnline = viewModel.currentSelectedChatOnlineStatus.collectAsState(),
              swipeableState = swipeableState
            )
        }

        HandleBackPress(
          coroutineScope = coroutineScope,
          swipeableState = swipeableState,
          composeNavigator = composeNavigator
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HandleBackPress(
    coroutineScope: CoroutineScope,
    swipeableState: SwipeableState<CenterScreenState>,
    composeNavigator: ComposeNavigator
) {
    BackHandler(enabled = true) {
        coroutineScope.launch {
            when (swipeableState.currentValue) {
                CenterScreenState.LEFT_ANCHORED -> swipeableState.animateTo(CenterScreenState.CENTER)
                CenterScreenState.CENTER -> swipeableState.animateTo(CenterScreenState.RIGHT_ANCHORED)
                CenterScreenState.RIGHT_ANCHORED -> composeNavigator.navigateUp()
            }
        }
    }
}