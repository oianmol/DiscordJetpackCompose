package dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordAppBar
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.Typography

@Composable
fun SubtitledAppBar(
  composeNavigator: ComposeNavigator,
  title: String,
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
    backgroundColor = DiscordColorProvider.colors.discordBackgroundOne.copy(alpha = 0.5f),
    elevation = 0.dp,
    title = {
      Column {
        Text(
          text = title,
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