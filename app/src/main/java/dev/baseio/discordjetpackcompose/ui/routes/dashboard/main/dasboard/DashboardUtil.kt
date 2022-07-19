package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.dasboard

import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.utils.getSampleServer

fun getFakeChatUserList(): MutableList<ChatUserEntity> {
  return mutableListOf<ChatUserEntity>().apply {
    add(
      ChatUserEntity(
        username = "MEE6",
        name = "MEE6",
        isOnline = false,
      )
    )
    repeat(20) {
      add(
        if (it % 2 == 0) {
          ChatUserEntity(
            username = "TestUser$it",
            name = "Test User $it",
            currentStatus = "Studying",
            isOnline = false,
          )
        } else {
          ChatUserEntity(
            username = "TestUser$it",
            name = "Test User $it",
            isOnline = true,
          )
        }
      )
    }
  }
}

fun getFakeServerList(): List<ServerEntity> {
  return listOf(
    getSampleServer(serverId = "1"),
    getSampleServer(serverId = "2"),
  )
}