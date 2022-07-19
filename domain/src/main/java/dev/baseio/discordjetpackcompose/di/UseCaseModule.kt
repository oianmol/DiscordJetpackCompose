package dev.baseio.discordjetpackcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.baseio.discordjetpackcompose.repositories.CountryRepo
import dev.baseio.discordjetpackcompose.repositories.FriendsRepo
import dev.baseio.discordjetpackcompose.repositories.MessagesRepo
import dev.baseio.discordjetpackcompose.repositories.ServerRepo
import dev.baseio.discordjetpackcompose.usecases.FetchCountriesUseCase
import dev.baseio.discordjetpackcompose.usecases.FetchFriendSuggestionsUseCase
import dev.baseio.discordjetpackcompose.usecases.FetchFriendsUseCase
import dev.baseio.discordjetpackcompose.usecases.GetServerUseCase
import dev.baseio.discordjetpackcompose.usecases.chat.FetchMessagesUseCase
import dev.baseio.discordjetpackcompose.usecases.chat.SendMessageUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideFetchCountriesUseCase(countryRepo: CountryRepo): FetchCountriesUseCase =
        FetchCountriesUseCase(countryRepo)

    @Provides
    @Singleton
    fun provideGetServerUseCase(serverRepo: ServerRepo) = GetServerUseCase(serverRepo = serverRepo)

    @Provides
    @Singleton
    fun provideFriendsSuggestionUseCase(friendsRepo: FriendsRepo) = FetchFriendSuggestionsUseCase(friendsRepo)

    @Provides
    @Singleton
    fun provideFriendsUseCase(friendsRepo: FriendsRepo) = FetchFriendsUseCase(friendsRepo)

    @Provides
    fun provideFetchMessageUseCase(messagesRepo: MessagesRepo) = FetchMessagesUseCase(messagesRepo)

    @Provides
    fun provideSendMessageUseCase(messagesRepo: MessagesRepo) = SendMessageUseCase(messagesRepo)
}