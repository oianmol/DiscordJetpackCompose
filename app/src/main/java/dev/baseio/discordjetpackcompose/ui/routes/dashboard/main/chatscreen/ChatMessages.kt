package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.chatscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
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
    messages?.let { safeMessages ->
      for (messageIndex in 0 until safeMessages.itemCount) {
        val message = safeMessages.peek(messageIndex)!!
        item {
          ChatMessageItem(message)
        }
      }
    }
    item {
      ChatBeginningHeader(
        userName = userName
      )
    }
  }
}