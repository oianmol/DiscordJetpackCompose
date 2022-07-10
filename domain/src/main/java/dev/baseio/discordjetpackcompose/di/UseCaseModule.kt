package dev.baseio.discordjetpackcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.baseio.discordjetpackcompose.repositories.CountryRepo
import dev.baseio.discordjetpackcompose.repositories.ServerRepo
import dev.baseio.discordjetpackcompose.usecases.FetchCountriesUseCase
import dev.baseio.discordjetpackcompose.usecases.GetServerUseCase
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
}