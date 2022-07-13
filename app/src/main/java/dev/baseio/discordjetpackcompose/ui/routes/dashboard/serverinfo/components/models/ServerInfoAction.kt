package dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class ServerInfoAction(
    val title: String,
    val titleColor: Color,
    val subtitle: String?,
    val trailingComposable: @Composable () -> Unit = {},
    val onClick: () -> Unit,
)
