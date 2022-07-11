package dev.baseio.discordjetpackcompose.ui.routes.dashboard.notification_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordAppBar
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.Typography

@Composable
fun NotificationScreen(
  title: String = "Notification Settings",
  subtitle: String? = "channel",
  composeNavigator: ComposeNavigator
) {

  val scaffoldState = rememberScaffoldState()
  val sysUiController = rememberSystemUiController()
  val colors = DiscordColorProvider.colors
  SideEffect {
    sysUiController.setSystemBarsColor(color = colors.discordBackgroundOne)
    sysUiController.setNavigationBarColor(color = colors.discordBackgroundOne)
  }

  DiscordScaffold(
    scaffoldState = scaffoldState,
    navigator = composeNavigator,
    topAppBar = {
      NotificationAppBar(composeNavigator, title, subtitle)
    },
    backgroundColor = colors.discordBackgroundOne
  ) { paddingValues ->
    Column(
      Modifier.padding(paddingValues)
    ) {

    }
  }
}

@Composable
private fun NotificationAppBar(
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
          contentDescription = "Back",
          modifier = Modifier.padding(start = 8.dp),
        )
      }
    },
    backgroundColor = Color.Transparent,
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