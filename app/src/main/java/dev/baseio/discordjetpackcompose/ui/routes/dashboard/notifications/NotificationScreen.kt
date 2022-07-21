package dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.components.NotificationFrequencySection
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.components.SubtitledAppBar
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.models.NotificationSettingsType
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.DiscordSwitchColors
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.models.ServerInfoAction
import dev.baseio.discordjetpackcompose.ui.theme.*

@Composable
fun NotificationScreen(
  composeNavigator: ComposeNavigator,
  screenType: NotificationSettingsType = NotificationSettingsType.SERVER,
  nameEntity: String? = "server"
) {
  val sysUiController = rememberSystemUiController()
  val colors = DiscordColorProvider.colors

  SideEffect {
    sysUiController.setSystemBarsColor(color = colors.discordBackgroundOne)
    sysUiController.setNavigationBarColor(color = colors.background)
  }

  val scrollState = rememberScrollState()
  val scaffoldState = rememberScaffoldState()
  var isMute by remember { mutableStateOf(false) }

  DiscordScaffold(
    modifier = Modifier.statusBarsPadding(),
    scaffoldState = scaffoldState,
    topAppBar = {
      SubtitledAppBar(
        composeNavigator,
        stringResource(id = R.string.notification_settings),
        nameEntity
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .background(color = create_server_screen)
        .verticalScroll(scrollState)
    ) {
      ServerChannelMuteSection(
        isMute = isMute,
        name = nameEntity,
        screenType = screenType
      ) {
        isMute = !isMute
      }
      SectionEndDivider()

      if (screenType != NotificationSettingsType.SERVER || !isMute) {
        SectionTitleHeader(
          stringResource = if (screenType == NotificationSettingsType.SERVER)
            R.string.notification_settings
          else
            R.string.frequency
        )

        NotificationFrequencySection(
          isMute = isMute
        )
        SectionEndDivider()
      }

      if (screenType == NotificationSettingsType.SERVER) {
        MentionsSection()
        SectionEndDivider()
        SectionTitleHeader(stringResource = R.string.notification_overrides)
        NotificationOverridesSection()
      }
    }
  }
}

@Composable
fun ServerChannelMuteSection(
  isMute: Boolean,
  name: String?,
  screenType: NotificationSettingsType,
  onMuteChange: () -> Unit
) {

  var isMute by remember { mutableStateOf(false) }

  val spannedString = buildAnnotatedString {
    withStyle(style = SpanStyle(fontSize = 14.sp)) {
      append(
        if (isMute) stringResource(id = R.string.unmute)
        else stringResource(id = R.string.mute)
      )
    }
    name?.let {
      withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)) {
        append(" $name")
      }
    }
  }

  Text(
    text = spannedString,
    modifier = Modifier
      .fillMaxWidth()
      .padding(12.dp)
  )

  val messageRes = when (screenType) {
    NotificationSettingsType.SERVER -> R.string.server_mute_msg
    NotificationSettingsType.CATEGORY -> R.string.category_mute_msg
    NotificationSettingsType.CHANNEL -> R.string.channel_mute_msg
  }

  Text(
    text = stringResource(id = messageRes),
    style = TextStyle(color = Color.LightGray, fontSize = 12.sp),
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 12.dp, vertical = 22.dp)
  )
}

@Composable
fun NotificationOverridesSection() {

}

@Composable
fun MentionsSection() {
  var suppressEveryoneMentions by remember { mutableStateOf(false) }
  var suppressRoleMentions by remember { mutableStateOf(false) }
  var suppressPush by remember { mutableStateOf(false) }

  MentionsItem(action = ServerInfoAction(
    trailingComposable = {
      Switch(
        checked = suppressEveryoneMentions,
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
      Switch(
        checked = suppressRoleMentions,
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
      Switch(
        checked = suppressPush,
        onCheckedChange = { isChecked -> suppressPush = isChecked },
        colors = DiscordSwitchColors
      )
    },
    title = "Mobile Push Notification",
    titleColor = Color.LightGray,
    subtitle = null,
    onClick = {
      suppressPush = !suppressPush
    }
  ))
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
    modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 16.dp),
    text = stringResource(id = stringResource).uppercase(),
    style = TextStyle(
      fontWeight = FontWeight.Bold,
      color = Color.Gray
    )
  )
}

@Composable
fun SectionEndDivider() {
  Spacer(
    modifier = Modifier
      .fillMaxWidth()
      .height(1.dp)
      .background(Color.LightGray)
  )
}
