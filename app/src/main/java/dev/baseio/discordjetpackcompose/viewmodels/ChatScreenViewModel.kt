package dev.baseio.discordjetpackcompose.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.entities.message.DiscordUrlMetaEntity
import dev.baseio.discordjetpackcompose.usecases.chat.FetchMessagesUseCase
import dev.baseio.discordjetpackcompose.usecases.chat.FetchUrlMetadataUseCase
import dev.baseio.discordjetpackcompose.usecases.chat.SendMessageUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

/**
 * UI state for the Chat screen
 */
data class ChatUiState(
  val message: String = "",
  val messageAction: String = "",
  val loading: Boolean = false,
)

@HiltViewModel
class ChatScreenViewModel @Inject constructor(
  private val fetchMessagesUseCase: FetchMessagesUseCase,
  private val sendMessageUseCase: SendMessageUseCase,
  private val fetchUrlMetadataUseCase: FetchUrlMetadataUseCase
) : ViewModel() {

  // UI state exposed to the UI
  private val _uiState = MutableStateFlow(ChatUiState(loading = true))
  val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

  var chatMessagesFlow = MutableStateFlow<Flow<PagingData<DiscordMessageEntity>>?>(null)

  fun fetchMessages() {
    chatMessagesFlow.value = fetchMessagesUseCase.performStreaming("1")
  }

  fun sendMessage(
    messageToSend: String,
    messageToReply: String,
    isReply: Boolean = false,
    url: String? = null
  ) {
    if (messageToSend.isNotEmpty()) {
      viewModelScope.launch {
        var discordUrlMetaEntity: DiscordUrlMetaEntity? = null
        url?.let { safeUrl ->
          discordUrlMetaEntity = fetchUrlMetadataUseCase.perform(safeUrl)
        }

        val message = DiscordMessageEntity(
          uuid = UUID.randomUUID().toString(),
          channelId = "1",
          message = messageToSend,
          userId = UUID.randomUUID().toString(),
          createdBy = "Person",
          createdDate = System.currentTimeMillis(),
          modifiedDate = System.currentTimeMillis(),
          replyTo = if (isReply) "Person" else "",
          replyToMessage = messageToReply,
          metaTitle = discordUrlMetaEntity?.title ?: "",
          metaDesc = discordUrlMetaEntity?.desc ?: "",
          metaImageUrl = discordUrlMetaEntity?.image ?: "",
          metaUrl = discordUrlMetaEntity?.url ?: ""
        )
        sendMessageUseCase.perform(message)
      }
      updateMessage("")
    }
  }

  fun updateMessage(message: String) {
    _uiState.update { chatUiState ->
      chatUiState.copy(message = message)
    }
  }

  fun updateMessageAction(action: String) {
    _uiState.update { chatUiState ->
      chatUiState.copy(messageAction = action)
    }
  }

  fun resetMessageAction() {
    _uiState.update { chatUiState ->
      chatUiState.copy(messageAction = "")
    }
  }
}