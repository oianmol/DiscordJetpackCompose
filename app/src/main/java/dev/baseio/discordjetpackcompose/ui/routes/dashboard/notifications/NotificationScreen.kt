package dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale.Companion.current
import androidx.compose.ui.text.intl.LocaleList.Companion.current
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordAppBar
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.DiscordSwitchColors
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.models.ServerInfoAction
import dev.baseio.discordjetpackcompose.ui.theme.*
import dev.baseio.discordjetpackcompose.ui.utils.clickableWithRipple
import java.util.*

@Composable
fun NotificationScreen(
  composeNavigator: ComposeNavigator,
  subtitle: String? = "channel"
) {
  val sysUiController = rememberSystemUiController()
  val colors = DiscordColorProvider.colors

  SideEffect {
    sysUiController.setSystemBarsColor(color = colors.discordBackgroundOne)
    sysUiController.setNavigationBarColor(color = colors.background)
  }

  val scrollState = rememberScrollState()
  val scaffoldState = rememberScaffoldState()

  DiscordScaffold(
    modifier = Modifier.statusBarsPadding(),
    scaffoldState = scaffoldState,
    topAppBar = {
      NotificationAppBar(composeNavigator, subtitle)
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(color = create_server_screen)
        .verticalScroll(scrollState)
    ) {
      ServerChannelMuteSection()
      NotificationSettingsSection()
      MentionsSection()
      NotificationOverridesSection()
    }
  }
}

@Composable
fun ServerChannelMuteSection() {
  SectionEndDivider()
}

@Composable
fun NotificationSettingsSection() {
  SectionTitleHeader(stringResource = R.string.notification_settings)
  MentionsItem(action = ServerInfoAction(
    trailingComposable = {
      //RadioButton(selected = , onClick = { /*TODO*/ }, colors =)
    },
    title = "All messages",
    titleColor = Color.LightGray,
    subtitle = null,
    onClick = {}
  ))
  SectionEndDivider()
}

@Composable
fun NotificationOverridesSection() {
  SectionTitleHeader(stringResource = R.string.notification_overrides)
}

@Composable
fun MentionsSection() {
  var suppressEveryoneMentions by remember { mutableStateOf(false) }
  var suppressRoleMentions by remember { mutableStateOf(false) }
  var suppressPush by remember { mutableStateOf(false) }

  MentionsItem(action = ServerInfoAction(
    trailingComposable = {
      Switch(checked = suppressEveryoneMentions,
        onCheckedChange = { isChecked -> suppressEveryoneMentions = isChecked },
        colors = DiscordSwitchColors
      )
    },
    title = "Suppress @Everyone and @here mentions",
    titleColor = Color.LightGray,
    subtitle = null,
    onClick = {
      suppressEveryoneMentions = !suppressEveryoneMentions
    }
  ))
  MentionsItem(action = ServerInfoAction(
    trailingComposable = {
      Switch(checked = suppressRoleMentions,
        onCheckedChange = { isChecked -> suppressRoleMentions = isChecked },
        colors = DiscordSwitchColors
      )
    },
    title = "Suppress All Role @mentions",
    titleColor = Color.LightGray,
    subtitle = null,
    onClick = {
      suppressRoleMentions = !suppressRoleMentions
    }
  ))
  MentionsItem(action = ServerInfoAction(
    trailingComposable = {
      Switch(checked = suppressPush,
        onCheckedChange = { isChecked -> suppressPush = isChecked },
        colors = DiscordSwitchColors
      )
    },
    title = "Mobile Push Notification",
    titleColor = Color.LightGray,
    subtitle = null,
    onClick = {
      suppressPush= !suppressPush
    }
  ))
  SectionEndDivider()
}

@Composable
fun MentionsItem(action: ServerInfoAction) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = action.onClick)
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
      Text(
        text = action.title,
        style = DirectMessageListTypography.h6,
        color = action.titleColor
      )
    }
    action.trailingComposable()
  }
}

@Composable
fun SectionTitleHeader(
  stringResource: Int
) {
  Text(
    modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 20.dp),
    text = stringResource(id = stringResource).uppercase(),
    style = TextStyle(
      fontWeight = FontWeight.Bold,
      color = Color.Gray
    )
  )
}

@Composable
fun SectionEndDivider() {
  Spacer(modifier = Modifier
    .fillMaxWidth()
    .height(1.dp)
    .background(Color.LightGray))
}

@Composable
private fun NotificationAppBar(
  composeNavigator: ComposeNavigator,
  subtitle: String?
) {
  DiscordAppBar(
    navigationIcon = {
      IconButton(onClick = {
        composeNavigator.navigateUp()
      }) {
        Icon(
          imageVector = Icons.Filled.ArrowBack,
          contentDescription = null,
          modifier = Modifier.padding(start = 8.dp),
        )
      }
    },
    backgroundColor = Color.Transparent,
    elevation = 0.dp,
    title = {
      Column {
        Text(
          text = stringResource(id = R.string.notification_settings),
          style = TextStyle(fontWeight = FontWeight.Bold, fontSize = Typography.h6.fontSize)
        )
        if (!subtitle.isNullOrBlank()) {
          Text(
            text = subtitle,
            style = TextStyle(fontSize = Typography.body2.fontSize)
          )
        }
      }
    }
  )
}