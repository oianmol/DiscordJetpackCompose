package dev.baseio.discordjetpackcompose.entities.server

/**
 * A ChannelEntity depicts a single channel inside a server.
 * @param id Uniquely identifies the channel
 * @param name Channel name that would be publicly displayed
 * @param type A channel can be of multiple types as defined inside [ChannelType]
 * @param category Defines which category does this channel belong to (there can be multiple channels inside a single category)
 * @param unreadCount How many unread messages are there inside this channel
 * */
data class ChannelEntity(
    val id: String,
    val name: String,
    val type: ChannelType,
    val category: String? = null,
    val unreadCount: Int = 0,
    val isUnread: Boolean = false,
)

enum class ChannelType {
    PUBLIC, PRIVATE, BROADCAST, PODCAST, CONVERSATION
}
