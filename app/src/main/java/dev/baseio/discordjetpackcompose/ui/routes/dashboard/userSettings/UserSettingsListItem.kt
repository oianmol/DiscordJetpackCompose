package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.models.SettingsEntity
import dev.baseio.discordjetpackcompose.ui.theme.DirectMessageListTypography
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider.colors
import dev.baseio.discordjetpackcompose.ui.theme.discord_settings_icon
import dev.baseio.discordjetpackcompose.ui.theme.user_settings_text
import dev.baseio.discordjetpackcompose.ui.utils.clickableWithRipple

@Composable
fun UserSettingsListItem(
  settingsEntity: SettingsEntity,
  onItemClick: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(colors.settingsBackground)
      .clickableWithRipple(onClick = onItemClick)
      .padding(top = 16.dp, bottom = 16.dp, end = 16.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start
  ) {
    Icon(
      painter = painterResource(id = settingsEntity.icon), contentDescription = null,
      modifier = Modifier.padding(start = 16.dp),
      tint = discord_settings_icon,
    )
    Text(
      text = settingsEntity.title, style = DirectMessageListTypography.h6,
      maxLines = 1,
      color = user_settings_text,
      modifier = Modifier.padding(start = 24.dp)
    )
    settingsEntity.currentStatus?.let { status ->
      Spacer(modifier = Modifier.weight(1f))
      Icon(
        painter = painterResource(id = R.drawable.ic_baseline_circle_24),
        contentDescription = null,
        modifier = Modifier
          .size(14.dp)
          .align(Alignment.CenterVertically),
        tint = Color(0xFF3DA45C)
      )
      Text(
        text = status,
        textAlign = TextAlign.End,
        modifier = Modifier
          .padding(start = 14.dp)
          .align(Alignment.CenterVertically)
      )
    }
  }
}