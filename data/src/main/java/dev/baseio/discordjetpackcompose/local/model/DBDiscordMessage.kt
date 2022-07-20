package dev.baseio.discordjetpackcompose.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "discordMessage")
data class DBDiscordMessage(
  @PrimaryKey val uuid: String,
  @ColumnInfo(name = "channelId") val channelId: String,
  @ColumnInfo(name = "message") val message: String,
  @ColumnInfo(name = "from") val userId: String,
  @ColumnInfo(name = "replyTo") val replyTo: String,
  @ColumnInfo(name = "replyToMessage") val replyToMessage: String,
  @ColumnInfo(name = "createdBy") val createdBy: String,
  @ColumnInfo(name = "createdDate") val createdDate: Long,
  @ColumnInfo(name = "modifiedDate") val modifiedDate: Long,
  @ColumnInfo(name = "metaTitle") val metaTitle: String,
  @ColumnInfo(name = "metaDesc") val metaDesc: String,
  @ColumnInfo(name = "metaImageUrl") val metaImageUrl: String,
  @ColumnInfo(name = "metaUrl") val metaUrl: String
)