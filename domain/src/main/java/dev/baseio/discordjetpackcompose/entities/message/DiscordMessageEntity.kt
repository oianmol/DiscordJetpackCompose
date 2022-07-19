package dev.baseio.discordjetpackcompose.entities.message

data class DiscordMessageEntity(
  val uuid: String,
  val channelId: String,
  val message: String,
  val userId: String,
  val replyTo: String,
  val replyToMessage: String,
  val createdBy: String,
  val createdDate: Long,
  val modifiedDate: Long,
)