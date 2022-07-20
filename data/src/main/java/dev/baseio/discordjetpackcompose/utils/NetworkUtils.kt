package dev.baseio.discordjetpackcompose.utils

import dev.baseio.discordjetpackcompose.data.BuildConfig
import dev.baseio.discordjetpackcompose.di.dispatcher.CoroutineDispatcherProvider
import dev.baseio.discordjetpackcompose.entities.NetworkState
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(
    coroutineDispatcherProvider: CoroutineDispatcherProvider,
    debugResponse: T,
    releaseResponse: suspend () -> T
): NetworkState<T> {
    return withContext(coroutineDispatcherProvider.io) {
        try {
            NetworkState.Success(
                data = if (BuildConfig.DEBUG) debugResponse
                else releaseResponse()
            )
        } catch (t: Throwable) {
            NetworkState.Failure(throwable = t)
        }
    }
}