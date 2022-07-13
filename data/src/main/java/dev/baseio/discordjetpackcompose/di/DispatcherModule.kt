package dev.baseio.discordjetpackcompose.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.baseio.discordjetpackcompose.di.dispatcher.CoroutineDispatcherProvider
import dev.baseio.discordjetpackcompose.di.dispatcher.RealCoroutineDispatcherProvider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DispatcherModule {
  @Provides
  @Singleton
  fun providesCoroutineDispatcher(): CoroutineDispatcherProvider {
    return RealCoroutineDispatcherProvider()
  }
}
