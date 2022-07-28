package dev.baseio.discordjetpackcompose.ui.routes.dashboard

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.baseio.discordjetpackcompose.entities.server.ServerEntity
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordRoute
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.createServer.CreateServer
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.friends.FriendsScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.invite.InviteScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.HomeScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.dasboard.DashboardScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.NotificationScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.UserSettings

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
        composable(DiscordScreen.NotificationSettings.name) {
            NotificationScreen(composeNavigator = composeNavigator)
        }
        composable(DiscordScreen.UserSettings.name) {
            UserSettings(composeNavigator = composeNavigator)
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.setupDashboardBottomNavScreens(
    composeNavigator: ComposeNavigator,
    sheetState: ModalBottomSheetState,
    onSelectServer: (String) -> Unit,
    shouldDisplayBottomBar: (Boolean) -> Unit,
    serverList: List<ServerEntity> // todo: refer a single source of truth for this serverlist without passing it around
) {
    navigation(
        startDestination = DiscordScreen.Home.route,
        route = DiscordRoute.DashboardBottomNav.name
    ) {
        composable(DiscordScreen.Home.route) {
            HomeScreen(
                composeNavigator = composeNavigator,
                serverList = serverList,
                onSelectServer = onSelectServer,
                sheetState = sheetState,
                shouldDisplayBottomBar = shouldDisplayBottomBar
            )
        }
        composable(DiscordScreen.Friends.route) {
            FriendsScreen(composeNavigator = composeNavigator)
        }
        composable(DiscordScreen.UserSettings.name) {
            UserSettings(composeNavigator = composeNavigator)
        }
    }
}