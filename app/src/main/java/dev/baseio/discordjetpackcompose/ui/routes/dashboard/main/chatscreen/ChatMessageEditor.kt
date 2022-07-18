package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.custom.MentionsTextField
import dev.baseio.discordjetpackcompose.R.drawable
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.MessageTypography
import dev.baseio.discordjetpackcompose.viewmodels.ChatScreenViewModel

@Composable
fun ChatMessageEditor(
  modifier: Modifier = Modifier,
  userName: State<String>,
  viewModel: ChatScreenViewModel
) {
  var mentionText by remember {
    mutableStateOf(TextFieldValue())
  }
  val messageText by viewModel.message.collectAsState()
  var showExtraButtons by remember { mutableStateOf(value = messageText.isEmpty()) }

  Row(
    modifier = modifier
      .height(48.dp)
      .padding(start = 8.dp, end = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    AnimatedVisibility(visible = showExtraButtons.not()) {
      MoreButton(
        modifier = Modifier
          .weight(1f)
          .clickable {
            showExtraButtons = true
          }
      )
    }

    AnimatedVisibility(visible = showExtraButtons) {
      ExtraButtons(
        modifier = Modifier.weight(1f)
      )
    }

    MessageEditorBar(
      modifier = Modifier
        .weight(2f),
      search = messageText,
      userName = userName,
      onMessageEdited = {
        showExtraButtons = false
        viewModel.message.value = it
      }
    )
    AnimatedVisibility(visible = messageText.isNotEmpty()) {
      SendMessageButton(
        modifier = Modifier
          .padding(start = 8.dp)
          .weight(1f),
        viewModel = viewModel,
        search = messageText
      )
    }
  }
}

@Composable
private fun ExtraButtons(
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.fillMaxHeight(),
    verticalAlignment = Alignment.CenterVertically
  ) {
    AttachmentButton(
      modifier = Modifier
        .padding(end = 8.dp)
    )
    GiftButton(
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
      MentionsTextField(
        onSpanUpdate = { update, spans, textRange ->

        }, mentionText = mentionText,
        maxLines = 4,
        cursorBrush = SolidColor(DiscordColorProvider.colors.textPrimary),
        onValueChange = {
          mentionText = it
        },
        textStyle = MessageTypography.subtitle1.copy(
          color = DiscordColorProvider.colors.textPrimary,
        ),
        decorationBox = { innerTextField ->
          ChatPlaceHolder(mentionText.text, Modifier, innerTextField)
        },
        modifier = Modifier
          .weight(1f)
          .fillMaxHeight()
          .padding(start = 16.dp, end = 16.dp)
        .padding(end = 8.dp)
    )
  }
}

@Composable
private fun MessageEditorBar(
  modifier: Modifier = Modifier,
  search: String,
  userName: State<String>,
  onMessageEdited: (String) -> Unit
) {
  Row(
    modifier = modifier
      .background(
        color = DiscordColorProvider.colors.chatEditor,
        shape = RoundedCornerShape(50)
      )
      .fillMaxHeight()
      .padding(start = 8.dp, end = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    MessageEditor(
      modifier = Modifier.weight(1f),
      search = search,
      userName = userName,
      onMessageEdited = { onMessageEdited(it) }
    )
    EmojiButton(
      modifier = Modifier
    )
  }
}

@Composable
private fun MessageEditor(
  modifier: Modifier = Modifier,
  search: String,
  userName: State<String>,
  onMessageEdited: (String) -> Unit
) {
  BasicTextField(
    modifier = modifier
      .fillMaxHeight()
      .padding(start = 16.dp, end = 16.dp),
    value = search,
    maxLines = 4,
    cursorBrush = SolidColor(DiscordColorProvider.colors.textPrimary),
    onValueChange = { onMessageEdited(it) },
    textStyle = MessageTypography.subtitle1.copy(
      color = DiscordColorProvider.colors.textPrimary,
    ),
    decorationBox = { innerTextField ->
      ChatPlaceHolder(
        search = search,
        userName = userName,
        innerTextField = innerTextField
      )
    }
    SendMessageButton(viewModel, mentionText.text) {
      mentionText = TextFieldValue()
    }
  }

@Composable
private fun SendMessageButton(
  modifier: Modifier = Modifier,
  viewModel: ChatScreenViewModel,
  message: String,
  onSent: () -> Unit
) {
  IconButton(
    modifier = modifier
      .padding(0.dp)
      .background(
        color = Color(0xFF5865f2),
        shape = CircleShape
      ),
    onClick = {
      viewModel.sendMessage(message)
      onSent()
    },
    enabled = message.isNotEmpty()
  ) {
    Icon(
      painter = painterResource(id = drawable.ic_send_rounded),
      contentDescription = null,
      tint = DiscordColorProvider.colors.brand
    )
  }
}

@Composable
private fun AttachmentButton(
  modifier: Modifier = Modifier
) {
  IconButton(
    modifier = modifier
      .padding(0.dp)
      .background(
        color = DiscordColorProvider.colors.chatEditor,
        shape = CircleShape
      ),
    onClick = {}
  ) {
    Icon(
      Icons.Default.Add,
      contentDescription = null,
      tint = DiscordColorProvider.colors.brand
    )
  }
}

@Composable
private fun GiftButton(
  modifier: Modifier = Modifier
) {
  IconButton(
    modifier = modifier
      .padding(0.dp)
      .background(
        color = DiscordColorProvider.colors.chatEditor,
        shape = CircleShape
      ),
    onClick = {}
  ) {
    Icon(
      Icons.Default.CardGiftcard,
      contentDescription = null,
      tint = DiscordColorProvider.colors.brand
    )
  }
}

@Composable
private fun MoreButton(
  modifier: Modifier = Modifier
) {
  Icon(
    modifier = modifier.padding(end = 4.dp),
    imageVector = Icons.Default.ArrowForwardIos,
    contentDescription = null,
    tint = Color(0xFFbabbbf)
  )
}

@Composable
private fun EmojiButton(
  modifier: Modifier = Modifier
) {
  Icon(
    modifier = modifier.padding(end = 4.dp),
    imageVector = Icons.Default.EmojiEmotions,
    contentDescription = null,
    tint = Color(0xFFbabbbf)
  )
}

@Composable
private fun ChatPlaceHolder(
  search: String,
  modifier: Modifier = Modifier,
  userName: State<String>,
  innerTextField: @Composable () -> Unit
) {
  Row(
    modifier = modifier.padding(top = 8.dp, bottom = 8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    if (search.isEmpty()) {
      Text(
        text = "Message @${userName.value}",
        style = MessageTypography.caption.copy(
          color = DiscordColorProvider.colors.textSecondary,
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(1f)
      )
    } else {
      innerTextField()
    }
  }
}