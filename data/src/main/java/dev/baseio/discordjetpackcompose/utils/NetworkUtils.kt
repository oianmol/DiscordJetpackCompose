package dev.baseio.discordjetpackcompose.utils

import dev.baseio.discordjetpackcompose.data.BuildConfig
import dev.baseio.discordjetpackcompose.entities.NetworkState

suspend fun <T> safeApiCall(
    debugResponse: T,
    releaseResponse: suspend () -> T
): NetworkState<T> {
    return try {
        NetworkState.Success(
            data = if (BuildConfig.DEBUG) debugResponse
            else releaseResponse()
        )
    } catch (t: Throwable) {
        NetworkState.Failure(throwable = t)
    }
}