package dev.baseio.discordjetpackcompose.ui.routes.dashboard.serverinfo.components.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class ServerQuickAction(
    @DrawableRes val icon: Int,
    val iconTint: Color? = null,
    val label: String,
    val onClick: () -> Unit,
)
