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
      entity.createdBy,
      entity.createdDate,
      entity.modifiedDate
    )
  }

  override fun mapToData(model: DiscordMessageEntity): DBDiscordMessage {
    return DBDiscordMessage(
      model.uuid,
      model.channelId,
      model.message,
      model.userId,
      model.createdBy,
      model.createdDate,
      model.modifiedDate,
    )
  }
}