package dev.baseio.discordjetpackcompose.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import dev.baseio.discordjetpackcompose.usecases.FetchCountriesUseCase
import dev.baseio.discordjetpackcompose.usecases.FetchFriendSuggestionsUseCase
import dev.baseio.discordjetpackcompose.usecases.FetchFriendsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val fetchFriendsUseCase: FetchFriendsUseCase,
    private val fetchFriendSuggestionsUseCase: FetchFriendSuggestionsUseCase,
) : ViewModel() {

    private val _friendsSuggestionsList = MutableStateFlow<Flow<List<ChatUserEntity>>>(emptyFlow())
    val friendsSuggestionsList = _friendsSuggestionsList.asStateFlow()

    private val _friendsList = MutableStateFlow<Flow<List<ChatUserEntity>>>(emptyFlow())
    val friendsList = _friendsList.asStateFlow()

    init {
        fetchFriendsList()
        fetchSuggestionsList()
    }

    private fun fetchSuggestionsList(){
        viewModelScope.launch {
            _friendsSuggestionsList.value = fetchFriendSuggestionsUseCase()
        }
    }

    private fun fetchFriendsList(){
        viewModelScope.launch {
            _friendsList.value = fetchFriendsUseCase()
        }
    }

}