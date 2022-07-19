package dev.baseio.discordjetpackcompose.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.baseio.discordjetpackcompose.repositories.*
import dev.baseio.discordjetpackcompose.di.dispatcher.CoroutineDispatcherProvider
import dev.baseio.discordjetpackcompose.entities.message.DiscordMessageEntity
import dev.baseio.discordjetpackcompose.local.dao.DiscordMessageDao
import dev.baseio.discordjetpackcompose.local.model.DBDiscordMessage
import dev.baseio.discordjetpackcompose.mappers.EntityMapper
import dev.baseio.discordjetpackcompose.repositories.CountryRepo
import dev.baseio.discordjetpackcompose.repositories.CountryRepoImpl
import dev.baseio.discordjetpackcompose.repositories.MessagesRepo
import dev.baseio.discordjetpackcompose.repositories.MessagesRepoImpl
import dev.baseio.discordjetpackcompose.repositories.ServerRepo
import dev.baseio.discordjetpackcompose.repositories.ServerRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCountryRepo(@ApplicationContext context: Context): CountryRepo =
        CountryRepoImpl(context = context)

    @Provides
    @Singleton
    fun provideServerRepo(): ServerRepo = ServerRepoImpl()

    @Provides
    @Singleton
    fun provideFriendsRepo(): FriendsRepo = FriendsRepoImpl()


    @Provides
    @Singleton
    fun provideMessagesRepo(
        discordMessageDao: DiscordMessageDao,
        entityMapper: EntityMapper<DiscordMessageEntity, DBDiscordMessage>,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): MessagesRepo = MessagesRepoImpl(
        discordMessageDao,
        entityMapper,
        coroutineDispatcherProvider
    )
}