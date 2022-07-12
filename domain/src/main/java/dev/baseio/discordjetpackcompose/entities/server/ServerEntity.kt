package dev.baseio.discordjetpackcompose.entities.server

import dev.baseio.discordjetpackcompose.utils.Constants

/**
 * Model class depicting a Discord server.
 * @param id Unique identifier for the server
 * @param name Name of the server
 * @param thumbnailUri URL to the thumbnail image for the server (Optional)
 * @param selectedAnimationUri URL of the animation which will be played whenever this server is selected in the list (Optional)
 * @param posterUri URL of the large poster/image to be displayed when this server is selected (Optional)
 * @param channels List of channels ([ChannelEntity]) this server contains
 * @param allChannelsUnreadCount Total unread message count of all the channels (Optional)
 * @param hasNitroSubscription Whether the channel has purchased the Discord Nitro subscription (this will lead to a special icon being displayed before the channel name)
 * @param totalMembersCount Displays how many members have joined this server so far
 * @param onlineMembersCount Displays how many members are online right now
 * @param description A short description about the server (Optional)
 * @param boostCount Displays how many Discord Boosts does this channel have
 * @param serverEmojiUris List of URIs for emojis which are specific to this server by default (Can be used in other servers too with the Discord Nitro Subscription)
 * */
data class ServerEntity(
    val id: String,
    val name: String,
    val thumbnailUri: String = Constants.MMLogoUrl,
    val selectedAnimationUri: String? = null,
    val posterUri: String? = null,
    val channels: List<ChannelEntity> = emptyList(),
    val allChannelsUnreadCount: Int = channels.sumOf { it.unreadCount },
    val hasNitroSubscription: Boolean,
    val totalMembersCount: Int = 0,
    val onlineMembersCount: Int = 0,
    val description: String? = null,
    val boostCount: Int = 0,
    val serverEmojiUris: List<String> = emptyList()
)
