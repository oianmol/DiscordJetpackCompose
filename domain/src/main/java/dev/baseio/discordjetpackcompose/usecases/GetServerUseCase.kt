package dev.baseio.discordjetpackcompose.usecases

import dev.baseio.discordjetpackcompose.repositories.ServerRepo
import javax.inject.Inject

class GetServerUseCase @Inject constructor(private val serverRepo: ServerRepo) {
    suspend operator fun invoke(serverId: String) = serverRepo.getServer(serverId = serverId)
}