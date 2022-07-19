package dev.baseio.discordjetpackcompose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.usecases.chat.FetchMessagesUseCase
import dev.baseio.discordjetpackcompose.usecases.chat.FetchUrlMetadataUseCase
import dev.baseio.discordjetpackcompose.usecases.chat.SendMessageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
  private val fetchMessagesUseCase: FetchMessagesUseCase,
  private val sendMessageUseCase: SendMessageUseCase,
  private val fetchUrlMetadataUseCase: FetchUrlMetadataUseCase
) : ViewModel() {

  var chatMessagesFlow = MutableStateFlow<Flow<PagingData<DiscordMessageEntity>>?>(null)
  var message = MutableStateFlow("")
  var messageAction = MutableStateFlow("")

  fun fetchMessages() {
    chatMessagesFlow.value = fetchMessagesUseCase.performStreaming("1")
  }

  fun sendMessage(
    messageToSend: String,
    messageToReply: String,
    isReply: Boolean = false
  ) {
    if (messageToSend.isNotEmpty()) {
      viewModelScope.launch {
        val message = DiscordMessageEntity(
          uuid = UUID.randomUUID().toString(),
          channelId = "1",
          message = messageToSend,
          userId = UUID.randomUUID().toString(),
          createdBy = "Person",
          createdDate = System.currentTimeMillis(),
          modifiedDate = System.currentTimeMillis(),
          replyTo = if (isReply) "Person" else "",
          replyToMessage = messageToReply
        )
        sendMessageUseCase.perform(message)
      }
      message.value = ""
    }
  }

  fun resetMessageAction() {
    messageAction.value = ""
  }

  fun fetchUrlMetadata(url: String) {
    viewModelScope.launch {
      val discordMessageEntity = fetchUrlMetadataUseCase.perform(url)
    }
  }
}