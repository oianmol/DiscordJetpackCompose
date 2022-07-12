package dev.baseio.discordjetpackcompose.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.baseio.discordjetpackcompose.local.model.DBDiscordMessage

@Dao
interface DiscordMessageDao {
  @Query("SELECT * FROM discordMessage")
  fun getAll(): List<DBDiscordMessage>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(messages: List<DBDiscordMessage>)

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(message: DBDiscordMessage)

  // The Int type parameter tells Room to use a PositionalDataSource object.
  @Query("SELECT * FROM discordMessage where channelId = :params ORDER BY createdDate DESC")
  fun messagesByDate(params: String?): PagingSource<Int, DBDiscordMessage>

  @Query("SELECT * from discordMessage where uuid like :uuid")
  fun getById(uuid: String): DBDiscordMessage
}