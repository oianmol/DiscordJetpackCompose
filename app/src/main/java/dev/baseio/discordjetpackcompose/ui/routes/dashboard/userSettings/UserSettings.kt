package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R.string
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
import dev.baseio.discordjetpackcompose.ui.components.DiscordAppBar
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.getAppInfoSettings
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.getAppSettings
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.getNitroSettingsList
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.getUserSettingsList
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.Typography
import dev.baseio.discordjetpackcompose.ui.theme.user_settings_bg

@Composable
fun UserSettings(
  composeNavigator: ComposeNavigator,
) {

  val scaffoldState = rememberScaffoldState()
  val sysUiController = rememberSystemUiController()
  var displayMenu by remember { mutableStateOf(false) }
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
            style = Typography.h5.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp
            ),
            textAlign = TextAlign.Start,
          )
        },
        actions = {
          IconButton(
            onClick = { composeNavigator.navigateAndClearBackStack(DiscordScreen.Login.name) }) {
            Icon(
              imageVector = Filled.Logout,
              contentDescription = stringResource(string.logout),
              modifier = Modifier.padding(start = 8.dp),
            )
          }

          IconButton(onClick = { displayMenu = !displayMenu }) {
            Icon(Icons.Default.MoreVert, null)
          }
          DropdownMenu(
            expanded = displayMenu,
            onDismissRequest = { displayMenu = false },
            modifier = Modifier.background(Color.White)
          ) {
            DropdownMenuItem(onClick = { }) {
              Text(
                text = stringResource(string.debug), color = Color.Black,
                textAlign = TextAlign.Center
              )
            }
          }
        },
        backgroundColor = user_settings_bg,
        elevation = 0.dp
      )
    },
    backgroundColor = colors.discordBackgroundOne,
    navigator = composeNavigator
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(user_settings_bg),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
      ) {
        UserSettingsList(
          userSettings = getUserSettingsList(context),
          nitroSettings = getNitroSettingsList(context),
          appSettings = getAppSettings(context),
          appInfo = getAppInfoSettings(context)
        )
      }
    }
  }
}