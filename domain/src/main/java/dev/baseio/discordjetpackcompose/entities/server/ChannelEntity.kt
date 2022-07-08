package dev.baseio.discordjetpackcompose.entities.server

/**
 * A ChannelEntity depicts a single channel inside a server.
 * @param channelType A channel can be of multiple types as defined inside [ChannelType]
 * @param category Defines which category does this channel belong to (there can be multiple channels inside a single category)
 * @param unreadCount How many unread messages are there inside this channel
 * */
data class ChannelEntity(
    val channelType: ChannelType,
    val category: String,
    val unreadCount: Int,
)

enum class ChannelType {
    CHANNEL, PRIVATE, BROADCAST, PODCAST, CONVERSATION
}
