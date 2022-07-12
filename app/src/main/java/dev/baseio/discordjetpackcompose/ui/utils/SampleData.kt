package dev.baseio.discordjetpackcompose.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import dev.baseio.discordjetpackcompose.entities.server.ChannelEntity
import dev.baseio.discordjetpackcompose.entities.server.ChannelType
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.SearchFilter
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.search.components.models.SearchSheetListItem
import dev.baseio.discordjetpackcompose.utils.Constants
import dev.baseio.discordjetpackcompose.utils.UIState

fun getSampleServerUIState(serverId: String) = UIState.Success(getSampleServer(serverId))

fun getSampleServer(serverId: String) = ServerEntity(
    id = serverId,
    name = "Test Server$serverId",
    posterUri = "https://images.pexels.com/photos/12641743/pexels-photo-12641743.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
    channels = listOf(
        ChannelEntity(
            id = "1",
            name = "announcements",
            type = ChannelType.PUBLIC,
            category = "INFO",
            unreadCount = 92,
            isUnread = true,
        ),
        ChannelEntity(
            id = "2",
            name = "youtube-feed",
            type = ChannelType.PUBLIC,
            category = "INFO",
        ),
        ChannelEntity(
            id = "3",
            name = "Total Members: 6.71K",
            type = ChannelType.PRIVATE,
        ),
        ChannelEntity(
            id = "4",
            name = "Online Members: 366",
            type = ChannelType.PRIVATE,
        ),
        ChannelEntity(
            id = "5",
            name = "android",
            type = ChannelType.PUBLIC,
            category = "PROGRAMMING",
            isUnread = true,
        ),
    ),
    hasNitroSubscription = serverId.toIntOrNull()?.rem(2) == 0,
    onlineMembersCount = 415,
    totalMembersCount = 1009,
    serverEmojiUris = mutableListOf<String>().apply {
        repeat(16) {
            add("https://cdn.shopify.com/s/files/1/1061/1924/files/Hugging_Face_Emoji_2028ce8b-c213-4d45-94aa-21e1a0842b4d_large.png?15202324258887420558")
        }
    },
    boostCount = 7,
)

fun getSampleSheetListItems() = mutableListOf<SearchSheetListItem>().apply {
    repeat(10) { index ->
        add(SearchSheetListItem(
            id = "channel1$index",
            itemType = SearchFilter.TextChannels,
            iconUri = Icons.Default.Tag,
            title = "announcements$index",
            subtitle = "!INFO",
            serverName = "Coding in Flow",
            unreadCount = 92
        ))
        add(SearchSheetListItem(
            id = "channel2$index",
            itemType = SearchFilter.Users,
            iconUri = Constants.MMLogoUrl,
            title = "YouTube$index",
            subtitle = null,
            serverName = "Youtube#1316",
            unreadCount = null
        ))
    }
}