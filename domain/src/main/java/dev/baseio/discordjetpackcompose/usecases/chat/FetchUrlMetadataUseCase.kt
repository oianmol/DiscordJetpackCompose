package  dev.baseio.discordjetpackcompose.usecases.chat

import dev.baseio.discordjetpackcompose.entities.message.DiscordUrlMetaEntity
import dev.baseio.discordjetpackcompose.repositories.MessagesRepo
import dev.baseio.discordjetpackcompose.usecases.BaseUseCase
import javax.inject.Inject

class FetchUrlMetadataUseCase @Inject constructor(private val messagesRepo: MessagesRepo) :
  BaseUseCase<DiscordUrlMetaEntity, String> {
  override suspend fun perform(params: String): DiscordUrlMetaEntity? {
    return messagesRepo.fetchUrlMetadata(params)
  }
}
