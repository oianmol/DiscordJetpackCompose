package dev.baseio.discordjetpackcompose.usecases

import dev.baseio.discordjetpackcompose.repositories.FriendsRepo
import javax.inject.Inject

class FetchFriendSuggestionsUseCase @Inject constructor(private val friendsRepo: FriendsRepo) {
    suspend operator fun invoke() = friendsRepo.fetchFriendSuggestions()
}