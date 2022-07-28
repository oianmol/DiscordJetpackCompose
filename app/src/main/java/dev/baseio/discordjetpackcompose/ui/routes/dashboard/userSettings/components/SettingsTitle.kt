package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.Typography
import dev.baseio.discordjetpackcompose.ui.theme.user_settings_text

@Composable
fun GetSettingsSubtitle(title: String) {
  Box(
    modifier = Modifier
      .background(DiscordColorProvider.colors.settingsBackground)
      .fillMaxWidth()
  ) {
    Text(
      text = title,
      style = Typography.h3.copy(
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp,
        color = user_settings_text
      ),
      textAlign = TextAlign.Start,
      modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
    )
  }
}