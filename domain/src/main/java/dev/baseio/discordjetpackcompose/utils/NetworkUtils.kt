package dev.baseio.discordjetpackcompose.utils

suspend inline fun <T> safeApiCall(crossinline block: suspend () -> T): T? {
    return try {
        block()
    } catch (e: Exception) {
        null
    }
}