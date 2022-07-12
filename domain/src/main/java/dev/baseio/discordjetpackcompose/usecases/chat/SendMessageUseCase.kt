package  dev.baseio.discordjetpackcompose.usecases.chat

import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.repositories.MessagesRepo
import dev.baseio.discordjetpackcompose.usecases.BaseUseCase
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val messagesRepo: MessagesRepo) :
  BaseUseCase<DiscordMessageEntity, DiscordMessageEntity> {
  override suspend fun perform(params: DiscordMessageEntity): DiscordMessageEntity {
    return messagesRepo.sendMessage(params)
  }
}
