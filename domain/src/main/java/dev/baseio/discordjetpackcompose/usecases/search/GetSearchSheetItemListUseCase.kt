package dev.baseio.discordjetpackcompose.usecases.search

import dev.baseio.discordjetpackcompose.repositories.ServerRepo
import javax.inject.Inject

class GetSearchSheetItemListUseCase @Inject constructor(private val serverRepo: ServerRepo) {
    suspend operator fun invoke() = serverRepo.getSearchSheetItemList()
}