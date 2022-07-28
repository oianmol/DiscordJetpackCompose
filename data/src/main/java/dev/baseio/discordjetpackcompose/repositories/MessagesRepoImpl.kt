package dev.baseio.discordjetpackcompose.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.baseio.discordjetpackcompose.di.dispatcher.CoroutineDispatcherProvider
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.entities.message.DiscordUrlMetaEntity
import dev.baseio.discordjetpackcompose.local.dao.DiscordMessageDao
import dev.baseio.discordjetpackcompose.local.model.DBDiscordMessage
import dev.baseio.discordjetpackcompose.mappers.EntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject

class MessagesRepoImpl @Inject constructor(
  private val discordMessageDao: DiscordMessageDao,
  private val entityMapper: EntityMapper<DiscordMessageEntity, DBDiscordMessage>,
  private val coroutineMainDispatcherProvider: CoroutineDispatcherProvider
) : MessagesRepo {

  private val cacheUrlMap = hashMapOf<String, DiscordUrlMetaEntity>()

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

  override suspend fun fetchUrlMetadata(url: String?): DiscordUrlMetaEntity? {
    val discordUrlMetaEntity = DiscordUrlMetaEntity()
    if (cacheUrlMap.containsKey(url)) return cacheUrlMap[url!!]

    withContext(coroutineMainDispatcherProvider.io) {
      val con = Jsoup.connect(url)
      val doc = con.userAgent("Mozilla").get()
      val ogTags = doc.select("meta[property^=og:]")
      when {
        ogTags.size > 0 ->
          ogTags.forEachIndexed { index, _ ->
            val tag = ogTags[index]
            when (tag.attr("property")) {
              "og:image" -> discordUrlMetaEntity.image = tag.attr("content")
              "og:description" -> discordUrlMetaEntity.desc = tag.attr("content")
              "og:url" -> discordUrlMetaEntity.url = tag.attr("content")
              "og:title" -> discordUrlMetaEntity.title = tag.attr("content")
            }
          }
      }
      cacheUrlMap[url!!] = discordUrlMetaEntity
    }
    return discordUrlMetaEntity
  }
}