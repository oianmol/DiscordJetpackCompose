package dev.baseio.discordjetpackcompose.ui.routes.dashboard

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordRoute
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.createServer.CreateServer
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.DashboardScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.FriendsScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.HomeScreen
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.main.InviteScreen
import dev.baseio.discordjetpackcompose.utils.getSampleServerList
import dev.baseio.discordjetpackcompose.viewmodels.DashboardScreenViewModel

fun NavGraphBuilder.dashboardRoute(
    composeNavigator: ComposeNavigator,
) {
    navigation(
        startDestination = DiscordScreen.Dashboard.name,
        route = DiscordRoute.Dashboard.name
    ) {
        composable(DiscordScreen.Dashboard.name) {
            val dashboardScreenVM = hiltViewModel<DashboardScreenViewModel>()

            LaunchedEffect(Unit) {
                dashboardScreenVM.getSearchSheetItemList()
            }

            DashboardScreen(
                composeNavigator = composeNavigator,
                searchSheetItemList = dashboardScreenVM.searchSheetItemList
            )
        }
        composable(DiscordScreen.Invite.name) {
            InviteScreen(composeNavigator = composeNavigator)
        }
        composable(DiscordScreen.CreateServer.name) {
            CreateServer(composeNavigator)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.setupDashboardBottomNavScreens(
    composeNavigator: ComposeNavigator,
    sheetState: ModalBottomSheetState,
    onSelectServer: (String) -> Unit,
    shouldDisplayBottomBar: (Boolean) -> Unit,
) {
    navigation(
        startDestination = DiscordScreen.Home.route,
        route = DiscordRoute.DashboardBottomNav.name
    ) {
        composable(DiscordScreen.Home.route) {
            HomeScreen(
                composeNavigator = composeNavigator,
                serverList = getSampleServerList(),
                onSelectServer = onSelectServer,
                sheetState = sheetState,
                shouldDisplayBottomBar = shouldDisplayBottomBar
            )
        }
        composable(DiscordScreen.Friends.route) {
            FriendsScreen(composeNavigator = composeNavigator)
        }
    }
}