package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R.string
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordAppBar
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.UserSettingsAppBar
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.getAppInfoSettings
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.getAppSettings
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.getNitroSettingsList
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.getUserSettingsList
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.Typography
import dev.baseio.discordjetpackcompose.ui.theme.user_settings_bg
import dev.baseio.discordjetpackcompose.utils.Constants

@Composable
fun UserSettings(
  composeNavigator: ComposeNavigator,
  userProfileImage: String = Constants.MMLogoUrl,
) {

  val scaffoldState = rememberScaffoldState()
  val sysUiController = rememberSystemUiController()
  val colors = DiscordColorProvider.colors
  val context = LocalContext.current

  SideEffect {
    sysUiController.setSystemBarsColor(color = colors.discordBackgroundOne)
    sysUiController.setNavigationBarColor(color = colors.discordBackgroundOne)
  }

  DiscordScaffold(
    scaffoldState = scaffoldState,
    topAppBar = {
      DiscordAppBar(
        title = {
          Text(
            text = stringResource(string.user_settings_title),
            style = Typography.h3.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp
            ),
            textAlign = TextAlign.Start,
          )
        },
        actions = {
          UserSettingsAppBar(composeNavigator)
        },
        backgroundColor = user_settings_bg,
        elevation = 0.dp
      )
    },
    navigator = composeNavigator
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(user_settings_bg),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start
    ) {
      UserSettingsList(
        userSettings = getUserSettingsList(context),
        nitroSettings = getNitroSettingsList(context),
        appSettings = getAppSettings(context),
        appInfo = getAppInfoSettings(context),
        profileImageUrl = userProfileImage
      )
    }
  }
}