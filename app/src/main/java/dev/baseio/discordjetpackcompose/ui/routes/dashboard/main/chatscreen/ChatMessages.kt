package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import dev.baseio.discordjetpackcompose.viewmodels.ChatScreenViewModel

@Composable
fun ChatMessages(
  modifier: Modifier = Modifier,
  viewModel: ChatScreenViewModel
) {
  val flowState by viewModel.chatMessagesFlow.collectAsState()
  val messages = flowState?.collectAsLazyPagingItems()
  val listState = rememberLazyListState()

  LazyColumn(state = listState, reverseLayout = true, modifier = modifier) {
    messages?.let {
      for (messageIndex in 0 until messages.itemCount) {
        val message = messages.peek(messageIndex)!!
        item {
          ChatMessageItem(message)
        }
      }
    }
  }
}