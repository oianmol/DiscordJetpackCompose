package dev.baseio.discordjetpackcompose.repositories

import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import kotlinx.coroutines.flow.Flow

interface FriendsRepo {
    suspend fun fetchFriendSuggestions(): Flow<List<ChatUserEntity>>
    suspend fun fetchFriends(): Flow<List<ChatUserEntity>>
}