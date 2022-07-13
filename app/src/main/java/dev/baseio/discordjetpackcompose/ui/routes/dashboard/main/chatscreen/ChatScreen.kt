package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.baseio.discordjetpackcompose.R.string
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordAppBar
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.OnlineIndicator
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.CenterScreenState
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.MessageTypography
import dev.baseio.discordjetpackcompose.viewmodels.ChatScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatScreen(
  modifier: Modifier = Modifier,
  composeNavigator: ComposeNavigator,
  viewModel: ChatScreenViewModel = hiltViewModel(),
  focusOpacity: Float,
  userName: State<String>,
  isOnline: State<Boolean>,
  swipeableState: SwipeableState<CenterScreenState>
) {
  val scaffoldState = rememberScaffoldState()

  SideEffect {
    viewModel.fetchMessages()
  }

  DiscordScaffold(
    modifier = Modifier.clip(RoundedCornerShape(2)),
    navigator = composeNavigator,
    scaffoldState = scaffoldState,
    backgroundColor = DiscordColorProvider.colors.chatBackground,
    topAppBar = {
      ChatScreenAppBar(
        name = userName.value,
        isOnline = isOnline.value,
        swipeableState = swipeableState
      )
    }
  ) { paddingValues ->
    Box(
      modifier = modifier
        .padding(top = paddingValues.calculateTopPadding() + 4.dp)
        .fillMaxSize()
        .background(Color.Black.copy(alpha = focusOpacity))
    ) {
      ChatScreenContent(
        modifier = Modifier,
        viewModel = viewModel,
        userName = userName
      )
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChatScreenAppBar(
  name: String,
  isOnline: Boolean,
  swipeableState: SwipeableState<CenterScreenState>
) {
  val coroutineScope = rememberCoroutineScope()
  DiscordAppBar(
    navigationIcon = {
      IconButton(onClick = {
        coroutineScope.launch {
          swipeableState.animateTo(CenterScreenState.RIGHT_ANCHORED)
        }
      }) {
        Icon(
          imageVector = Filled.Menu,
          contentDescription = stringResource(string.menu),
          modifier = Modifier.padding(start = 8.dp),
        )
      }
    },
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Filled.AlternateEmail,
          contentDescription = null,
          tint = Color.Gray
        )
        Text(
          modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .wrapContentWidth(),
          maxLines = 1,
          style = MessageTypography.h2,
          overflow = TextOverflow.Ellipsis,
          text = name,
          color = DiscordColorProvider.colors.onSurface
        )
        OnlineIndicator(
          modifier = Modifier,
          isOnline = isOnline
        )
      }
    },
    actions = {
      Icon(
        imageVector = Filled.PhoneInTalk,
        contentDescription = null,
        modifier = Modifier.padding(end = 16.dp),
      )
      Icon(
        imageVector = Filled.Videocam,
        contentDescription = null,
        modifier = Modifier.padding(end = 16.dp),
      )
      Icon(
        imageVector = Filled.People,
        contentDescription = null,
        modifier = Modifier.padding(end = 16.dp),
      )
    },
    backgroundColor = DiscordColorProvider.colors.chatTopBar,
    elevation = 0.dp
  )
}