package dev.baseio.discordjetpackcompose.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.discordjetpackcompose.entities.NetworkState
import dev.baseio.discordjetpackcompose.entities.UIState
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.usecases.GetServerUseCase
import dev.baseio.discordjetpackcompose.utils.ioScope
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getServerUseCase: GetServerUseCase
): ViewModel() {
    var currentServerEntity: UIState<ServerEntity> by mutableStateOf(UIState.Empty)
        private set

    fun getServer(serverId: String) {
        ioScope {
            currentServerEntity = UIState.Loading
            currentServerEntity = when(val result = getServerUseCase(serverId = serverId)) {
                is NetworkState.Failure -> { UIState.Failure(throwable = result.throwable) }
                is NetworkState.Success -> { UIState.Success(data = result.data) }
            }
        }
    }
}