package dev.baseio.discordjetpackcompose.ui.routes.onboarding


import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordRoute
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.login.LoginScreen
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.welcome.WelcomeScreen
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.register.RegisterScreen

fun NavGraphBuilder.onBoardingRoute(
    composeNavigator: ComposeNavigator,
) {
    navigation(
      startDestination = DiscordScreen.Welcome.name,
      route = DiscordRoute.OnBoarding.name
    ) {
        composable(DiscordScreen.Welcome.name) {
            WelcomeScreen(composeNavigator)
        }
        composable(DiscordScreen.Login.name){
            LoginScreen(composeNavigator)
        }
        composable(DiscordScreen.Register.name) {
            RegisterScreen(composeNavigator)
        }
    }

}