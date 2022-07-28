package dev.baseio.discordjetpackcompose.navigator

import androidx.navigation.NamedNavArgument

sealed class DiscordScreen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    val name: String = route.appendArguments(navArguments)

    object Welcome : DiscordScreen("welcome")
    object Register : DiscordScreen("register")
    object Login : DiscordScreen("login")
    object Dashboard : DiscordScreen("dashboard")
    object Friends : DiscordScreen("friends")
    object CreateServer : DiscordScreen("createServer")
    object NotificationSettings : DiscordScreen("notificationSettings")
    object Invite : DiscordScreen("invite")
    object UserSettings: DiscordScreen("userSettings")
    object Home : DiscordScreen("home")
    object Search : DiscordScreen("search")
}

sealed class DiscordRoute(val name: String) {
    object OnBoarding : DiscordRoute("onboardingRoute")
    object Dashboard : DiscordRoute("dashboardRoute")
    object DashboardBottomNav: DiscordRoute("dashboardBottomNav")
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