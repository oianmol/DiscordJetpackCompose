package dev.baseio.discordjetpackcompose.ui.routes.dashboard

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordRoute
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.createServer.CreateServer
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.friends.FriendsScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.invite.InviteScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.dasboard.DashboardScreen

fun NavGraphBuilder.dashboardRoute(
    composeNavigator: ComposeNavigator,
) {
    navigation(
        startDestination = DiscordScreen.Dashboard.name,
        route = DiscordRoute.Dashboard.name
    ) {
        composable(DiscordScreen.Dashboard.name) {
            DashboardScreen(composeNavigator = composeNavigator)
        }
        composable(DiscordScreen.Friends.name) {
            FriendsScreen(composeNavigator = composeNavigator)
        }
        composable(DiscordScreen.Invite.name) {
            InviteScreen(composeNavigator = composeNavigator)
        }
        composable(DiscordScreen.CreateServer.name) {
            CreateServer(composeNavigator)
        }
    }
}