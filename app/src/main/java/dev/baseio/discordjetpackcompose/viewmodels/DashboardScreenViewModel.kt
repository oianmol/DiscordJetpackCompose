package dev.baseio.discordjetpackcompose.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.discordjetpackcompose.entities.NetworkState
import dev.baseio.discordjetpackcompose.entities.UIState
import dev.baseio.discordjetpackcompose.entities.search.SearchSheetListItemEntity
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.usecases.GetServerUseCase
import dev.baseio.discordjetpackcompose.usecases.search.GetSearchSheetItemListUseCase
import dev.baseio.discordjetpackcompose.usecases.server.GetServerListUseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardScreenViewModel @Inject constructor(
    private val getServerUseCase: GetServerUseCase,
    private val getSearchSheetItemListUseCase: GetSearchSheetItemListUseCase,
    private val getServerListUseCase: GetServerListUseCase,
): ViewModel() {
    var currentServerEntity: UIState<ServerEntity> by mutableStateOf(UIState.Empty)
        private set

    var searchSheetItemList = mutableStateListOf<SearchSheetListItemEntity>()
        private set

    var serverList = mutableStateListOf<ServerEntity>()
        private set

    var searchSheetServerList = mutableStateListOf<ServerEntity>()
        private set

    fun getServer(serverId: String) {
        viewModelScope.launch {
            currentServerEntity = UIState.Loading
            currentServerEntity = when(val result = getServerUseCase(serverId = serverId)) {
                is NetworkState.Failure -> { UIState.Failure(throwable = result.throwable) }
                is NetworkState.Success -> { UIState.Success(data = result.data) }
            }
        }
    }

    fun getServers(serverIds: List<String>) {
        serverIds.forEach { serverId ->
            viewModelScope.launch {
                val result = getServerUseCase(serverId = serverId)
                if (result is NetworkState.Success) {
                    searchSheetServerList.add(result.data)
                }
            }
        }
    }

    fun getSearchSheetItemList() {
        viewModelScope.launch {
            if (searchSheetItemList.isNotEmpty()) searchSheetItemList.clear()
            searchSheetItemList.addAll(
                when (val result = getSearchSheetItemListUseCase()) {
                    is NetworkState.Failure -> emptyList()
                    is NetworkState.Success -> result.data
                }
            )
        }
    }

    fun getServerList() {
        viewModelScope.launch {
            if (serverList.isNotEmpty()) serverList.clear()
            serverList.addAll(
                when(val result = getServerListUseCase()) {
                    is NetworkState.Failure -> emptyList()
                    is NetworkState.Success -> result.data
                }
            )
        }
    }

    private val _currentSelectedChatUsername = MutableStateFlow("")
    val currentSelectedChatUsername: StateFlow<String> = _currentSelectedChatUsername.asStateFlow()

    private val _currentSelectedChatOnlineStatus = MutableStateFlow(false)
    val currentSelectedChatOnlineStatus: StateFlow<Boolean> =
        _currentSelectedChatOnlineStatus.asStateFlow()

    fun setCurrentSelectedChatUserName(name: String) {
        _currentSelectedChatUsername.value = name
    }

    fun setCurrentSelectedChatOnlineStatus(isOnline: Boolean) {
        _currentSelectedChatOnlineStatus.value = isOnline
    }
}