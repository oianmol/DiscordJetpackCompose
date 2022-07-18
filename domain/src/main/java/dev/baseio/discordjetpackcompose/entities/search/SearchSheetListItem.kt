package dev.baseio.discordjetpackcompose.entities.search

enum class SearchFilter(val title: String, val sign: Char) {
    Users(title = "Users", sign = '@'),
    TextChannels(title = "Text Channels", sign = '#'),
    VoiceChannels(title = "Voice Channels", sign = '!'),
    Servers(title = "Servers", sign = '*'),
}

data class SearchSheetListItem(
    val id: String,
    val itemType: SearchFilter,
    val iconUri: Any?,
    val title: String,
    val subtitle: String? = null,
    val serverName: String? = null,
    val unreadCount: Int? = null,
)
