package dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.components.models

import dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.SearchFilter

data class SearchSheetListItem(
    val id: String,
    val itemType: SearchFilter,
    val iconUri: Any?,
    val title: String,
    val subtitle: String? = null,
    val serverName: String? = null,
    val unreadCount: Int? = null,
)
