package dev.baseio.discordjetpackcompose.usecases

import dev.baseio.discordjetpackcompose.repositories.FriendsRepo
import javax.inject.Inject

class FetchFriendsUseCase @Inject constructor(private val friendsRepo: FriendsRepo) {
    suspend operator fun invoke() = friendsRepo.fetchFriends()
}