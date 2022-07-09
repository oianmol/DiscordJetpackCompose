package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator

@Composable
fun DiscordScaffold(
  scaffoldState: ScaffoldState,
  navigator: ComposeNavigator?,
  backgroundColor: Color = Color(0xFF383842),
  contentColor: Color = Color(0xFF383842),
  topAppBar: @Composable () -> Unit = {
    DiscordAppBar(
      navigationIcon = {
        IconButton(onClick = {
          navigator?.navigateUp()
        }) {
          Icon(
            imageVector = Filled.ArrowBack,
            contentDescription = "Back",
            modifier = Modifier.padding(start = 8.dp),
            tint = Color.White
          )
        }
      },
      backgroundColor = Color.Transparent,
      elevation = 0.dp
    )
  },
  content: @Composable (PaddingValues) -> Unit
) {
  Scaffold(
    backgroundColor = backgroundColor,
    contentColor = contentColor,
    modifier = Modifier.statusBarsPadding(),
    scaffoldState = scaffoldState,
    topBar = topAppBar,
    snackbarHost = {
      scaffoldState.snackbarHostState
    },
    content = content
  )
}