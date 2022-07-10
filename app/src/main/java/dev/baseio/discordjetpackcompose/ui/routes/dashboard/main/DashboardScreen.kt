package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
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
import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.ServerDrawer
import dev.baseio.discordjetpackcompose.ui.utils.getSampleServer
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

private enum class DrawerTypes {
    LEFT, RIGHT
}

private enum class CenterScreenState {
    LEFT_ANCHORED, CENTER, RIGHT_ANCHORED
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    serverList: List<ServerEntity> = listOf(
        getSampleServer(serverId = "1"),
        getSampleServer(serverId = "2"),
    ),
    chatUserList: List<ChatUserEntity> = mutableListOf<ChatUserEntity>().apply {
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
    composeNavigator: ComposeNavigator
) {
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

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        ServerDrawer(modifier = leftDrawerModifier,
            serverList = serverList,
            chatUserList = chatUserList,
            onAnyItemSelected = { isSelected ->
                isAnyItemSelectedInServers = isSelected
                coroutineScope.launch { swipeableState.animateTo(CenterScreenState.CENTER) }
            },
            onAddButtonClick = {})
        Box(
            modifier = rightDrawerModifier
                .fillMaxHeight()
                .fillMaxWidth(0.85f)
                .background(Color.Cyan)
                .align(Alignment.CenterEnd)
        ) {}

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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Green)
            )
        }
    }
}