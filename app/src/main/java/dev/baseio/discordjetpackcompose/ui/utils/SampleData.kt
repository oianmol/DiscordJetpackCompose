package dev.baseio.discordjetpackcompose.ui.utils

import dev.baseio.discordjetpackcompose.entities.server.ChannelEntity
import dev.baseio.discordjetpackcompose.entities.server.ChannelType
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
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
    hasNitroSubscription = serverId.toIntOrNull()?.rem(2) == 0
)