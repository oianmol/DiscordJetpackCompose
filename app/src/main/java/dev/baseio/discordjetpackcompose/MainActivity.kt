package dev.baseio.discordjetpackcompose

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
        super.onCreate(savedInstanceState)
        installSplashScreen()
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
        /**For Making the App FullScreen*/
        WindowCompat.setDecorFitsSystemWindows(window, false)

        /**For getting the system top bar on down swipe
         * Not Sure if we want this*/
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
    }
}

