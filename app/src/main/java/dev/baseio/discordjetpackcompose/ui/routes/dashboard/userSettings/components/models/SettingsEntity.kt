package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.models

data class SettingsEntity(
  val title: String,
  val icon: Int,
  val currentStatus: String? = null,
  val statusIcon: String? = null
)