package  dev.baseio.discordjetpackcompose.usecases.chat

import androidx.paging.PagingData
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.repositories.MessagesRepo
import dev.baseio.discordjetpackcompose.usecases.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchMessagesUseCase @Inject constructor(private val messagesRepo: MessagesRepo) :
  BaseUseCase<PagingData<DiscordMessageEntity>, String> {
  override fun performStreaming(params: String?): Flow<PagingData<DiscordMessageEntity>> {
    return messagesRepo.fetchMessages(params)
  }
}
