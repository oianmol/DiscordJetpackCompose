package dev.baseio.discordjetpackcompose.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.DiscordSurface
import dev.baseio.discordjetpackcompose.ui.theme.primarySurface

@Composable
fun DiscordAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = DiscordColorProvider.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = AppBarDefaults.TopAppBarElevation,
) {
    DiscordSurface(
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation
    ) {
        TopAppBar(
            title, modifier, navigationIcon, actions, backgroundColor, contentColor, elevation
        )
    }
}