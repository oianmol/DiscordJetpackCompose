package dev.baseio.discordjetpackcompose.usecases.server

import dev.baseio.discordjetpackcompose.repositories.ServerRepo
import javax.inject.Inject

class GetServerListUseCase @Inject constructor(private val serverRepo: ServerRepo) {
    suspend operator fun invoke() = serverRepo.getServerList()
}