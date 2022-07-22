package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.dasboard

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import dev.baseio.discordjetpackcompose.entities.ChatUserEntity
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
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

fun NavHostController.navigateTab(screen: DiscordScreen) {
  navigate(screen.route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    popUpTo(graph.findStartDestination().id) {
      saveState = true
    }
    // Avoid multiple copies of the same destination when
    // reselecting the same item
    launchSingleTop = true
    // Restore state when reselecting a previously selected item
    restoreState = true
  }
}