package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components

import androidx.compose.foundation.background
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.R.string
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen.Login

@Composable
fun UserSettingsAppBar(composeNavigator: ComposeNavigator) {
  var displayMenu by remember { mutableStateOf(false) }
  IconButton(
    onClick = { composeNavigator.navigateAndClearBackStack(Login.name) }) {
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
}