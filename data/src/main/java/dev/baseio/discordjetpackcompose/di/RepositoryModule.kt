package dev.baseio.discordjetpackcompose.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.baseio.discordjetpackcompose.repositories.*
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
    fun provideFriendsRepo():FriendsRepo = FriendsRepoImpl()
}