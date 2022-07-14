package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.dasboard

import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.ui.utils.getSampleServer

fun getFakeChatUserList(): MutableList<ChatUserEntity> {
  return mutableListOf<ChatUserEntity>().apply {
    repeat(20) {
      add(
        if (it % 2 == 0) {
          ChatUserEntity(
            username = "testusername$it",
            name = "Test User",
            currentStatus = "Studying",
            isOnline = false,
          )
        } else {
          ChatUserEntity(
            username = "testusername$it",
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