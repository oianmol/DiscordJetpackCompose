package dev.baseio.discordjetpackcompose.ui.routes.dashboard.notifications.models

data class OverrideItem(
  val title: String,
  val subtitle: String?,
  val type: OverrideType,
  val frequencyType: FrequencyType
)

enum class OverrideType {
  CHANNEL,
  CATEGORY
}