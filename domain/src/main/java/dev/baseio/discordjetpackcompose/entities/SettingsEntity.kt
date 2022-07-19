package dev.baseio.discordjetpackcompose.entities

data class SettingsEntity(
  val title: String,
  val icon: Int,
  val currentStatus: String? = null,
  val statusIcon: String? = null
)