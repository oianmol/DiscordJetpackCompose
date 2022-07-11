package dev.baseio.discordjetpackcompose.ui.routes.dashboard.main


import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.components.DiscordAppBar
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.Typography

@Composable
fun FriendsScreen(composeNavigator: ComposeNavigator) {
    val scaffoldState = rememberScaffoldState()

    val sysUiController = rememberSystemUiController()
    val colors = DiscordColorProvider.colors
    SideEffect {
        sysUiController.setSystemBarsColor(color = colors.discordBackgroundOne)
        sysUiController.setNavigationBarColor(color = colors.discordBackgroundOne)
    }

    DiscordScaffold(
        scaffoldState = scaffoldState, topAppBar = {
            DiscordAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.friends),
                        style = Typography.h5.copy(
                            fontSize = 18.sp
                        )
                    )
                },
                actions = {

                }
            )
        },
        backgroundColor = colors.discordBackgroundOne,
        navigator = composeNavigator
    ) {

    }
}