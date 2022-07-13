package dev.baseio.discordjetpackcompose.repositories

import androidx.paging.PagingData
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import kotlinx.coroutines.flow.Flow

interface MessagesRepo {
  fun fetchMessages(params: String?): Flow<PagingData<DiscordMessageEntity>>
  suspend fun sendMessage(params: DiscordMessageEntity): DiscordMessageEntity
}