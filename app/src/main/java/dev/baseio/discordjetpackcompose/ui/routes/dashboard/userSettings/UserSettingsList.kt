package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.navigationBarsPadding
import dev.baseio.discordjetpackcompose.R.string
import dev.baseio.discordjetpackcompose.entities.SettingsEntity
import dev.baseio.discordjetpackcompose.ui.theme.Typography
import dev.baseio.discordjetpackcompose.ui.theme.user_settings_text

@Composable
fun UserSettingsList(
  userSettings: List<SettingsEntity>,
  nitroSettings: List<SettingsEntity>,
  appSettings: List<SettingsEntity>,
  appInfo: List<SettingsEntity>
) {
  LazyColumn {
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = stringResource(string.settings_user_settings),
        style = Typography.h3.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 15.sp,
          color = user_settings_text
        ),
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
      )
    }
    items(userSettings.size) { index ->
      UserSettingsListItem(settingsEntity = userSettings[index],
        onItemClick = {})
    }
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Divider(color = Color.DarkGray, thickness = 1.dp)
    }
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = stringResource(string.settings_nitro_settings),
        style = Typography.h3.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 15.sp,
          color = user_settings_text
        ),
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
      )
    }
    items(nitroSettings.size) { index ->
      UserSettingsListItem(settingsEntity = nitroSettings[index],
        onItemClick = {})
    }
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Divider(color = Color.DarkGray, thickness = 1.dp)
    }
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = stringResource(string.settings_app_settings),
        style = Typography.h3.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 15.sp,
          color = user_settings_text
        ),
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
      )

    }
    items(appSettings.size) { index ->
      UserSettingsListItem(settingsEntity = appSettings[index],
        onItemClick = {})
    }
    item {
      Spacer(modifier = Modifier.height(8.dp))
      Divider(color = Color.DarkGray, thickness = 1.dp)
    }
    item {
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = stringResource(string.settings_app_info),
        style = Typography.h3.copy(
          fontWeight = FontWeight.SemiBold,
          fontSize = 15.sp,
          color = user_settings_text
        ),
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
      )
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