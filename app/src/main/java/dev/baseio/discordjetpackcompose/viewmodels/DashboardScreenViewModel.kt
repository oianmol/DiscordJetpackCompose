package dev.baseio.discordjetpackcompose.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardScreenViewModel @Inject constructor() : ViewModel() {
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

