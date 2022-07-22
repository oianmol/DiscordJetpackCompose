package dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.components.*
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.models.NotificationSettingsType
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
        .background(color = colors.background)
        .verticalScroll(scrollState)
    ) {

      // Notifications Mute Un-Mute
      NotificationsMuteSection(
        isMute = isMute,
        name = nameEntity,
        screenType = screenType
      ) {
        isMute = !isMute
      }
      SectionEndDivider()

      // Notification Settings and Frequency
      if (screenType != NotificationSettingsType.SERVER || !isMute) {
        SectionTitleHeader(
          stringResource = if (screenType == NotificationSettingsType.SERVER)
            R.string.notification_settings
          else
            R.string.frequency
        )

        NotificationFrequencySection(isMute = isMute)
        SectionEndDivider()
      }

      if (screenType == NotificationSettingsType.SERVER) {
        // Mentions Sections
        MentionsSection(isMute)
        SectionEndDivider()

        // Notification override section
        SectionTitleHeader(stringResource = R.string.notification_overrides)
        NotificationOverridesList()
        
        Spacer(modifier = Modifier.height(24.dp))
      }
    }
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
