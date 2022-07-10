package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.contentColorFor

@Composable
fun DiscordScaffold(
    scaffoldState: ScaffoldState,
    navigator: ComposeNavigator? = null,
    backgroundColor: Color = DiscordColorProvider.colors.background,
    contentColor: Color = DiscordColorProvider.colors.contentColorFor(DiscordColorProvider.colors.background),
    topAppBar: @Composable () -> Unit = {
        DiscordAppBar(
            navigationIcon = {
                IconButton(onClick = {
                    navigator?.navigateUp()
                }) {
                    Icon(
                        imageVector = Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    },
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        modifier = Modifier.statusBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = topAppBar,
        snackbarHost = {
            scaffoldState.snackbarHostState
        },
        content = content
    )
}