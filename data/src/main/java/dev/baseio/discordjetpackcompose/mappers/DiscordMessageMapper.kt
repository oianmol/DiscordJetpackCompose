package dev.baseio.discordjetpackcompose.mappers

import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.local.model.DBDiscordMessage
import javax.inject.Inject

class DiscordMessageMapper @Inject constructor() : EntityMapper<DiscordMessageEntity, DBDiscordMessage> {

  override fun mapToDomain(entity: DBDiscordMessage): DiscordMessageEntity {
    return DiscordMessageEntity(
      entity.uuid,
      entity.channelId,
      entity.message,
      entity.userId,
      entity.replyTo,
      entity.replyToMessage,
      entity.createdBy,
      entity.createdDate,
      entity.modifiedDate,
      entity.metaTitle,
      entity.metaDesc,
      entity.metaImageUrl,
      entity.metaUrl
    )
  }

  override fun mapToData(model: DiscordMessageEntity): DBDiscordMessage {
    return DBDiscordMessage(
      model.uuid,
      model.channelId,
      model.message,
      model.userId,
      model.replyTo,
      model.replyToMessage,
      model.createdBy,
      model.createdDate,
      model.modifiedDate,
      model.metaTitle,
      model.metaDesc,
      model.metaImageUrl,
      model.metaUrl
    )
  }
}