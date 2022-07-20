package dev.baseio.discordjetpackcompose.repositories

import androidx.paging.PagingData
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.entities.message.DiscordUrlMetaEntity
import kotlinx.coroutines.flow.Flow

interface MessagesRepo {
  fun fetchMessages(params: String?): Flow<PagingData<DiscordMessageEntity>>
  suspend fun sendMessage(params: DiscordMessageEntity): DiscordMessageEntity
  suspend fun fetchUrlMetadata(url: String?): DiscordUrlMetaEntity?
}