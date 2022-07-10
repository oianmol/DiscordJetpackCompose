package dev.baseio.discordjetpackcompose.navigator

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class DiscordScreen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    val name: String = route.appendArguments(navArguments)

    object Welcome : DiscordScreen("welcome")
    object Register : DiscordScreen("register")
    object Login : DiscordScreen("login")
    object Dashboard : DiscordScreen("dashboard")
}

sealed class DiscordRoute(val name: String) {
    object OnBoarding : DiscordRoute("onboardingRoute")
    object Dashboard : DiscordRoute("dashboardRoute")
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