package dev.baseio.discordjetpackcompose.entities

sealed class UIState<out T> {
    object Empty: UIState<Nothing>()
    object Loading: UIState<Nothing>()
    class Success<T>(val data: T): UIState<T>()
    class Failure(val throwable: Throwable): UIState<Nothing>()
}
