package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.MessageTypography
import dev.baseio.discordjetpackcompose.viewmodels.ChatScreenViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatMessages(
  modifier: Modifier = Modifier,
  userName: State<String>,
  viewModel: ChatScreenViewModel
) {
  val flowState by viewModel.chatMessagesFlow.collectAsState()
  val messages = flowState?.collectAsLazyPagingItems()
  val listState = rememberLazyListState()
  LazyColumn(state = listState, reverseLayout = true, modifier = modifier.padding(top = 24.dp)) {
    var lastDrawnMessage: String? = null
    messages?.let { safeMessages ->
      for (messageIndex in 0 until safeMessages.itemCount) {
        val message = safeMessages.peek(messageIndex)!!
        item {
          ChatMessageItem(
            message = message
          )
        }

        // Add chat date header
        lastDrawnMessage = message.createdDate.calendar().formattedFullDate()
        if (!isLastMessage(messageIndex, messages)) {
          val nextMessageMonth =
            messages.peek(messageIndex + 1)?.createdDate?.calendar()?.formattedFullDate()
          if (nextMessageMonth != lastDrawnMessage) {
            stickyHeader {
              ChatHeader(message.createdDate)
            }
          }
        } else {
          stickyHeader {
            ChatHeader(message.createdDate)
          }
        }
      }
    }
    item(key = 0) {
      ChatBeginningHeader(
        modifier = Modifier,
        lastDrawnMessage = lastDrawnMessage,
        userName = userName
      )
    }
  }
}

private fun isLastMessage(
  messageIndex: Int,
  messages: LazyPagingItems<DiscordMessageEntity>
) = messageIndex == messages.itemCount.minus(1)

@Composable
private fun ChatHeader(createdDate: Long) {
  Row(Modifier.padding(start = 8.dp, end = 8.dp), verticalAlignment = Alignment.CenterVertically) {
    Divider(
      modifier = Modifier.weight(2f),
      color = DiscordColorProvider.colors.textSecondary.copy(alpha = 0.8f),
      thickness = 0.2.dp
    )
    Text(
      modifier = Modifier.weight(2f, fill = true),
      text = createdDate.calendar().formattedFullDate(),
      textAlign = TextAlign.Center,
      style = MessageTypography.overline.copy(
        fontWeight = FontWeight.SemiBold,
        color = DiscordColorProvider.colors.textSecondary.copy(alpha = 0.8f)
      )
    )
    Divider(
      modifier = Modifier.weight(2f),
      color = DiscordColorProvider.colors.textSecondary.copy(alpha = 0.8f),
      thickness = 0.2.dp
    )
  }
}