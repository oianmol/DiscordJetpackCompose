package dev.baseio.discordjetpackcompose.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.baseio.discordjetpackcompose.local.dao.DiscordMessageDao
import dev.baseio.discordjetpackcompose.local.database.DiscordDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
  @Provides
  @Singleton
  fun provideDatabase(@ApplicationContext context: Context): DiscordDatabase {
    return Room.inMemoryDatabaseBuilder(
      context,
      DiscordDatabase::class.java,
    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
  }

  @Provides
  @Singleton
  fun providesDiscordMessageDao(discordDatabase: DiscordDatabase): DiscordMessageDao =
    discordDatabase.discordMessageDao()
}