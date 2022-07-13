package dev.baseio.discordjetpackcompose.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.baseio.discordjetpackcompose.local.dao.DiscordMessageDao
import dev.baseio.discordjetpackcompose.local.model.DBDiscordMessage

@Database(
  entities = [DBDiscordMessage::class],
  version = 1,
  exportSchema = false
)
abstract class DiscordDatabase : RoomDatabase() {
  abstract fun discordMessageDao(): DiscordMessageDao
}