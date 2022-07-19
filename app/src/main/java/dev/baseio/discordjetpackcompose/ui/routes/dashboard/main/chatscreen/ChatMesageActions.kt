package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.InsertEmoticon
import androidx.compose.material.icons.filled.MarkAsUnread
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.MessageTypography
import dev.baseio.discordjetpackcompose.ui.utils.Drawables

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MessageActionsBottomSheet(
  sheetState: ModalBottomSheetState,
  replyAction: () -> Unit = {}
) {
  ModalBottomSheetLayout(
    modifier = Modifier,
    sheetState = sheetState,
    sheetContent = {
      MessageActionBottomSheetContent(
        replyAction = replyAction
      )
    },
    sheetShape = RoundedCornerShape(0),
    sheetBackgroundColor = DiscordColorProvider.colors.surface,
    sheetContentColor = DiscordColorProvider.colors.onPrimary,
    scrimColor = Color.Black.copy(alpha = 0.32f),
    content = {}
  )
}

@Composable
fun MessageActionBottomSheetContent(
  replyAction: () -> Unit = {}
) {
  Column(
    modifier = Modifier
      .wrapContentHeight()
      .fillMaxWidth()
  ) {
    MessageActionsEmojisList()
    MessageActionsList(
      replyAction = replyAction
    )
  }
}

@Composable
fun MessageActionsEmojisList() {
  Row(
    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
  ) {
    EmojiActionButton(emojiId = Drawables.ic_emoji_1)
    EmojiActionButton(emojiId = Drawables.ic_emoji_2)
    EmojiActionButton(emojiId = Drawables.ic_emoji_3)
    EmojiActionButton(emojiId = Drawables.ic_emoji_4)
    EmojiActionButton(emojiId = Drawables.ic_emoji_5)
    IconButton(
      modifier = Modifier
        .background(
          color = DiscordColorProvider.colors.chatEditor,
          shape = CircleShape
        ),
      onClick = {}
    ) {
      Icon(
        Icons.Default.InsertEmoticon,
        contentDescription = null,
        tint = DiscordColorProvider.colors.brand
      )
    }
  }
}

@Composable
fun MessageActionsList(
  replyAction: () -> Unit = {}
) {
  Column(
    modifier = Modifier.padding(top = 16.dp, bottom = 48.dp)
  ) {
    MessageActionItem(Icons.Default.Edit, "Edit")
    MessageActionItem(Icons.Default.Reply, "Reply", action = replyAction)
    MessageActionItem(Icons.Default.FileCopy, "Copy Text")
    MessageActionItem(Icons.Default.Delete, "Delete")
    MessageActionItem(Icons.Default.Person, "Profile")
    MessageActionItem(Icons.Default.Pin, "Pin")
    MessageActionItem(Icons.Default.Share, "Share")
    MessageActionItem(Icons.Default.MarkAsUnread, "Mark Unread")
    MessageActionItem(Icons.Default.FileCopy, "Copy ID")
  }
}

@Composable
fun MessageActionItem(
  imageVector: ImageVector,
  text: String,
  action: () -> Unit = {}
) {
  Row(
    modifier = Modifier
      .clickable { action() },
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 16.dp),
      imageVector = imageVector,
      contentDescription = null,
      tint = DiscordColorProvider.colors.brand
    )
    Text(
      modifier = Modifier
        .padding(start = 32.dp, top = 16.dp, bottom = 16.dp)
        .fillMaxWidth(),
      style = MessageTypography.caption,
      text = text,
      color = DiscordColorProvider.colors.onSurface
    )
  }
}

@Composable
private fun EmojiActionButton(
  modifier: Modifier = Modifier,
  emojiId: Int
) {
  IconButton(
    modifier = modifier
      .padding(end = 8.dp)
      .background(
        color = DiscordColorProvider.colors.chatEditor,
        shape = CircleShape
      ),
    onClick = {}
  ) {
    Icon(
      painterResource(id = emojiId),
      contentDescription = null,
      tint = Color.Unspecified
    )
  }
}