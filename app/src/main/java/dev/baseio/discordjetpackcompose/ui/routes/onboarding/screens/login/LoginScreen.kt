package dev.baseio.discordjetpackcompose.ui.routes.onboarding.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.onboarding.commonui.CenteredTitleSubtitle

@Composable
fun LoginScreen(composeNavigator: ComposeNavigator) {
    val scaffoldState = rememberScaffoldState()

    DiscordScaffold(scaffoldState = scaffoldState, navigator = composeNavigator) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            CenteredTitleSubtitle(title = R.string.welcome_back, subtitle = R.string.login_excited)

        }
    }
}