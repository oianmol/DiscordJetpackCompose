package dev.baseio.discordjetpackcompose.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.baseio.discordjetpackcompose.di.dispatcher.CoroutineDispatcherProvider
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.local.dao.DiscordMessageDao
import dev.baseio.discordjetpackcompose.local.model.DBDiscordMessage
import dev.baseio.discordjetpackcompose.mappers.EntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MessagesRepoImpl @Inject constructor(
  private val discordMessageDao: DiscordMessageDao,
  private val entityMapper: EntityMapper<DiscordMessageEntity, DBDiscordMessage>,
  private val coroutineMainDispatcherProvider: CoroutineDispatcherProvider
) : MessagesRepo {

  override fun fetchMessages(params: String?): Flow<PagingData<DiscordMessageEntity>> {
    return Pager(PagingConfig(pageSize = 20)) {
      discordMessageDao.messagesByDate(params)
    }.flow.mapLatest { it -> it.map { entityMapper.mapToDomain(it) } }
  }

  override suspend fun sendMessage(params: DiscordMessageEntity): DiscordMessageEntity {
    return withContext(coroutineMainDispatcherProvider.io) {
      discordMessageDao.insert(entityMapper.mapToData(params))
      params
    }
  }
}