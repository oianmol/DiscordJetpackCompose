package dev.baseio.discordjetpackcompose.repositories

import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FriendsRepoImpl() : FriendsRepo {
    override suspend fun fetchFriendSuggestions(): Flow<List<ChatUserEntity>> {
        return flowOf(
            listOf(
                ChatUserEntity("Eren yeager", name = "Eren", isOnline = false),
                ChatUserEntity("Kamado Tanjiro", name = "Monjiro", isOnline = false)
            )
        )
    }

    override suspend fun fetchFriends(): Flow<List<ChatUserEntity>> {
        return flowOf(
            listOf(
                ChatUserEntity("Mikasa Ackerman", name = "Mikasa", isOnline = false, currentStatus = "Offline"),
                ChatUserEntity("Gojo Satoru", name = "Nanami", isOnline = true, currentStatus = "Online"),
                ChatUserEntity("Akagami Shanks", name = "Shanks", isOnline = false, currentStatus = "Offline"),
                ChatUserEntity("Roronoa Zoro", name = "Zoro", isOnline = false, currentStatus = "Offline"),
                ChatUserEntity("Itachi Uchiha", name = "Itachi", isOnline = true, currentStatus = "Online"),
                ChatUserEntity("Monkey D Luffy", name = "Luffy", isOnline = true, currentStatus = "Online"),
                ChatUserEntity("Hatake Kakashi", name = "Kakashi", isOnline = false, currentStatus = "Offline"),
                ChatUserEntity("Gol D Roger", name = "Roger", isOnline = false, currentStatus = "Offline"),
                ChatUserEntity("Portgas D Ace", name = "Ace", isOnline = false, currentStatus = "Offline"),
                ChatUserEntity("Nico Robin", name = "Robin", isOnline = false, currentStatus = "Offline"),
                ChatUserEntity("Anya Forger", name = "Anya", isOnline = false, currentStatus = "Offline"),
                ChatUserEntity("Boa Hancock", name = "Boa", isOnline = false, currentStatus = "Offline"),
            )
        )
    }
}