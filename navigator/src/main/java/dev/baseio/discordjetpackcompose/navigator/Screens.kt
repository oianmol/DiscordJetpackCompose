package dev.baseio.discordjetpackcompose.navigator

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class DiscordScreen(
  val route: String,
  val navArguments: List<NamedNavArgument> = emptyList()
) {
  val name: String = route.appendArguments(navArguments)

  // onboarding
  object GettingStarted : DiscordScreen("gettingStarted")
  // dashboard
  object Dashboard : DiscordScreen(
    "Dashboard",
    navArguments = listOf(navArgument("channelId") { type = NavType.StringType })
  ) {
    fun createRoute(channelId: String) =
      route.replace("{${navArguments.first().name}}", channelId)
  }

  object CreateChannelsScreen : DiscordScreen("CreateChannelsScreen")
  object CreateNewChannel : DiscordScreen("CreateNewChannel")
  object CreateNewDM : DiscordScreen("CreateNewDM")

}

sealed class DiscordRoute(val name: String) {
  object OnBoarding : DiscordRoute("onboarding")
  object Dashboard : DiscordRoute("dashboard")
}

private fun String.appendArguments(navArguments: List<NamedNavArgument>): String {
  val mandatoryArguments = navArguments.filter { it.argument.defaultValue == null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "/", prefix = "/") { "{${it.name}}" }
    .orEmpty()
  val optionalArguments = navArguments.filter { it.argument.defaultValue != null }
    .takeIf { it.isNotEmpty() }
    ?.joinToString(separator = "&", prefix = "?") { "${it.name}={${it.name}}" }
    .orEmpty()
  return "$this$mandatoryArguments$optionalArguments"
}