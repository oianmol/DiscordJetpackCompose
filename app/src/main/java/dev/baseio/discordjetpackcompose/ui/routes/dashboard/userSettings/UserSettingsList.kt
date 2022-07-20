package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import dev.baseio.discordjetpackcompose.R.string
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.GetSettingsSubtitle
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.GetTopComponent
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.models.SettingsEntity
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider.colors

@Composable
fun UserSettingsList(
  userSettings: List<SettingsEntity>,
  nitroSettings: List<SettingsEntity>,
  appSettings: List<SettingsEntity>,
  appInfo: List<SettingsEntity>,
  profileImageUrl: String,
  username: String = "mutual",
  discordTag: String = "#8976"
) {
  val lazyListState = rememberLazyListState()

  LazyColumn(state = lazyListState, modifier = Modifier.background(colors.settingsBackground)) {
    item {
      GetTopComponent(lazyListState, profileImageUrl, username, discordTag)
    }

    item {
      GetSettingsSubtitle(title = stringResource(string.settings_user_settings))
    }

    items(userSettings.size) { index ->
      UserSettingsListItem(settingsEntity = userSettings[index],
        onItemClick = {})
    }

    item {
      Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
      GetSettingsSubtitle(title = stringResource(string.settings_nitro_settings))
    }

    items(nitroSettings.size) { index ->
      UserSettingsListItem(settingsEntity = nitroSettings[index],
        onItemClick = {})
    }

    item {
      Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
      GetSettingsSubtitle(title = stringResource(string.settings_app_settings))
    }

    items(appSettings.size) { index ->
      UserSettingsListItem(settingsEntity = appSettings[index],
        onItemClick = {})
    }

    item {
      Divider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
      GetSettingsSubtitle(title = stringResource(string.settings_app_info))
    }

    items(appInfo.size) { index ->
      UserSettingsListItem(settingsEntity = appInfo[index],
        onItemClick = {})
    }

    item {
      Spacer(
        modifier = Modifier
          .navigationBarsPadding()
          .padding(vertical = 32.dp)
      )
    }
  }
}