package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components

import android.content.Context
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings.components.models.SettingsEntity
import dev.baseio.discordjetpackcompose.ui.utils.Drawables
import dev.baseio.discordjetpackcompose.ui.utils.Strings

fun getUserSettingsList(context: Context): List<SettingsEntity> {
  return listOf(

    SettingsEntity(
      title = context.getString(Strings.settings_set_status),
      icon = Drawables.ic_baseline_account_circle_24,
      currentStatus = "Online"
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_my_account),
      icon = Drawables.ic_baseline_account_box_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_user_profile),
      icon = Drawables.ic_baseline_edit_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_privacy),
      icon = Drawables.ic_baseline_security_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_auth_apps),
      icon = Drawables.ic_baseline_vpn_key_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_connections),
      icon = Drawables.ic_baseline_laptop_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_scan_qr),
      icon = Drawables.ic_baseline_qr_code_24,
    )
  )
}

fun getNitroSettingsList(context: Context): List<SettingsEntity> {
  return listOf(

    SettingsEntity(
      title = context.getString(Strings.settings_subscribe_today),
      icon = Drawables.ic_baseline_subscriptions_24
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_boosts),
      icon = Drawables.ic_baseline_account_box_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_nitro_gifting),
      icon = Drawables.ic_baseline_card_giftcard_24,
    )
  )
}

fun getAppSettings(context: Context): List<SettingsEntity> {
  return listOf(

    SettingsEntity(
      title = context.getString(Strings.settings_voice_video),
      icon = Drawables.ic_baseline_mic_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_notification),
      icon = Drawables.ic_baseline_notification_important_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_text_images),
      icon = Drawables.ic_baseline_image_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_appearance),
      icon = Drawables.ic_baseline_color_lens_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_accessibility),
      icon = Drawables.ic_baseline_accessibility_new_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_behavior),
      icon = Drawables.ic_baseline_settings_applications_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_language),
      icon = Drawables.ic_baseline_language_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_activity_status),
      icon = Drawables.ic_baseline_vpn_key_24,
    )
  )
}

fun getAppInfoSettings(context: Context): List<SettingsEntity> {
  return listOf(

    SettingsEntity(
      title = context.getString(Strings.settings_change_log),
      icon = Drawables.ic_baseline_info_24
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_support),
      icon = Drawables.ic_baseline_contact_support_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_debug_logs),
      icon = Drawables.ic_baseline_info_24,
    ),

    SettingsEntity(
      title = context.getString(Strings.settings_acknowledgement),
      icon = Drawables.ic_baseline_info_24,
    )

  )
}