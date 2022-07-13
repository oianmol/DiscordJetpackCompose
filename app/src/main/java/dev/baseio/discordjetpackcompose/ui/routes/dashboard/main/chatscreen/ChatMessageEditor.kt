package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.MessageTypography
import dev.baseio.discordjetpackcompose.viewmodels.ChatScreenViewModel

@Composable
fun ChatMessageEditor(
  modifier: Modifier = Modifier,
  viewModel: ChatScreenViewModel
) {
  val search by viewModel.message.collectAsState()
  Row(
    modifier
      .padding(start = 32.dp, end = 8.dp)
      .height(42.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Row(
      modifier = Modifier
        .background(
          color = DiscordColorProvider.colors.chatEditor,
          shape = RoundedCornerShape(50)
        )
        .fillMaxHeight()
        .weight(1f)
        .padding(start = 8.dp, end = 8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      BasicTextField(
        value = search,
        maxLines = 4,
        cursorBrush = SolidColor(DiscordColorProvider.colors.textPrimary),
        onValueChange = {
          viewModel.message.value = it
        },
        textStyle = MessageTypography.subtitle1.copy(
          color = DiscordColorProvider.colors.textPrimary,
        ),
        decorationBox = { innerTextField ->
          ChatPlaceHolder(search, Modifier, innerTextField)
        },
        modifier = Modifier
          .weight(1f)
          .fillMaxHeight()
          .padding(start = 16.dp, end = 16.dp)
      )
    }
    SendMessageButton(viewModel, search)
  }
}

@Composable
private fun SendMessageButton(
  viewModel: ChatScreenViewModel,
  search: String,
  modifier: Modifier = Modifier
) {
  IconButton(
    modifier = modifier
      .padding(8.dp)
      .background(
        color = Color(0xFF5865f2),
        shape = CircleShape
      ),
    onClick = {
      viewModel.sendMessage(search)
    },
    enabled = search.isNotEmpty()
  ) {
    Icon(
      Icons.Default.Send,
      contentDescription = null,
      tint = DiscordColorProvider.colors.brand
    )
  }
}

@Composable
private fun ChatPlaceHolder(
  search: String,
  modifier: Modifier = Modifier,
  innerTextField: @Composable () -> Unit
) {
  Row(
    modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (search.isEmpty()) {
      Text(
        text = "Message Thomas",
        style = MessageTypography.subtitle1.copy(
          color = DiscordColorProvider.colors.textSecondary,
        ),
        modifier = Modifier.weight(1f)
      )
    } else {
      innerTextField()
    }
  }
}