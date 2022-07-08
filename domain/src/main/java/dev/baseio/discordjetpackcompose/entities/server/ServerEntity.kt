package dev.baseio.discordjetpackcompose.entities.server

/**
 * Model class depicting a Discord server.
 * @param id Unique identifier for the server
 * @param name Name of the server
 * @param thumbnailUri URL to the thumbnail image for the server (Optional)
 * @param selectedAnimationUri URL of the animation which will be played whenever this server is selected in the list (Optional)
 * @param posterUri URL of the large poster/image to be displayed when this server is selected (Optional)
 * @param channels List of channels ([ChannelEntity]) this server contains
 * */
data class ServerEntity(
    val id: String,
    val name: String,
    val thumbnailUri: String = "https://cdn.iconscout.com/icon/free/png-256/discord-3691244-3073764.png",
    val selectedAnimationUri: String? = null,
    val posterUri: String? = null,
    val channels: List<ChannelEntity> = emptyList()
)
