package dev.baseio.discordjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordRoute
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.dashboardRoute
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.onBoardingRoute
import dev.baseio.discordjetpackcompose.ui.theme.DiscordJetpackComposeTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var composeNavigator: ComposeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DiscordJetpackCompose)

        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            val navController = rememberNavController()

            LaunchedEffect(Unit) {
                composeNavigator.handleNavigationCommands(navController)
            }
            DiscordJetpackComposeTheme {

                ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                    NavHost(
                        navController = navController,
                        startDestination = DiscordRoute.OnBoarding.name,
                    ) {
                        onBoardingRoute(composeNavigator)
                        dashboardRoute(composeNavigator)
                    }
                }
            }
        }
    }
}

