package dev.baseio.discordjetpackcompose.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.local.model.DBDiscordMessage
import dev.baseio.discordjetpackcompose.mappers.DiscordMessageMapper
import dev.baseio.discordjetpackcompose.mappers.EntityMapper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataMappersModule {

  @Binds
  @Singleton
  abstract fun bindDiscordMessageMapper(
    discordMessageMapper: DiscordMessageMapper
  ): EntityMapper<DiscordMessageEntity, DBDiscordMessage>
}