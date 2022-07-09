package dev.baseio.discordjetpackcompose.repositories

import dev.baseio.discordjetpackcompose.entities.NetworkState
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity

interface ServerRepo {
    suspend fun getServer(serverId: String): NetworkState<ServerEntity>
}