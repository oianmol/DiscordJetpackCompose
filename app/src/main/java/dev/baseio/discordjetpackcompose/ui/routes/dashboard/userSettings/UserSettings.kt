package dev.baseio.discordjetpackcompose.ui.routes.dashboard.userSettings

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.baseio.discordjetpackcompose.R
import dev.baseio.discordjetpackcompose.R.string
import dev.baseio.discordjetpackcompose.entities.SettingsEntity
import dev.baseio.discordjetpackcompose.navigator.ComposeNavigator
import dev.baseio.discordjetpackcompose.navigator.DiscordScreen
import dev.baseio.discordjetpackcompose.ui.components.DiscordAppBar
import dev.baseio.discordjetpackcompose.ui.components.DiscordScaffold
import dev.baseio.discordjetpackcompose.ui.routes.dashboard.components.OnlineIndicator
import dev.baseio.discordjetpackcompose.ui.theme.DiscordColorProvider
import dev.baseio.discordjetpackcompose.ui.theme.Typography
import dev.baseio.discordjetpackcompose.ui.theme.user_settings_bg
import dev.baseio.discordjetpackcompose.ui.utils.rememberCoilImageRequest
import dev.baseio.discordjetpackcompose.utils.Constants

@Composable
fun UserSettings(
  composeNavigator: ComposeNavigator,
  userProfileImage: String = Constants.MMLogoUrl,
) {

  val scaffoldState = rememberScaffoldState()
  val sysUiController = rememberSystemUiController()
  var displayMenu by remember { mutableStateOf(false) }
  val colors = DiscordColorProvider.colors

  SideEffect {
    sysUiController.setSystemBarsColor(color = colors.discordBackgroundOne)
    sysUiController.setNavigationBarColor(color = colors.discordBackgroundOne)
  }

  DiscordScaffold(
    scaffoldState = scaffoldState,
    topAppBar = {
      DiscordAppBar(
        title = {
          Text(
            text = stringResource(string.user_settings_title),
            style = Typography.h5.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp
            ),
            textAlign = TextAlign.Start,
          )
        },
        actions = {
          IconButton(onClick = { composeNavigator.navigateAndClearBackStack(DiscordScreen.Login.name) }) {
            Icon(
              imageVector = Filled.Logout,
              contentDescription = stringResource(string.logout),
              modifier = Modifier.padding(start = 8.dp),
            )
          }

          IconButton(onClick = { displayMenu = !displayMenu }) {
            Icon(Icons.Default.MoreVert, null)
          }
          DropdownMenu(
            expanded = displayMenu,
            onDismissRequest = { displayMenu = false },
            modifier = Modifier.background(Color.White)
          ) {
            DropdownMenuItem(onClick = { }) {
              Text(
                text = stringResource(string.debug), color = Color.Black,
                textAlign = TextAlign.Center
              )
            }
          }
        },
        backgroundColor = user_settings_bg,
        elevation = 0.dp
      )
    },
    backgroundColor = colors.discordBackgroundOne,
    navigator = composeNavigator
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(user_settings_bg),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start
    ) {
      Row(
        modifier = Modifier
          .height(40.dp)
          .fillMaxWidth()
          .background(Color(0xFF00a6d5))
      ) {
      }
      Box(
        Modifier
          .fillMaxWidth()
          .wrapContentHeight()
      ) {
        Row(
          modifier = Modifier
            .height(32.dp)
            .fillMaxWidth()
            .background(Color(0xFF00a6d5))
        ) {
        }
        Column(
          modifier = Modifier
            .padding(start = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
        ) {
          OnlineIndicator(
            isOnline = true,
            indicatorSize = 22.dp
          ) {
            AsyncImage(
              model = rememberCoilImageRequest(data = userProfileImage),
              contentDescription = stringResource(string.settings_profile_image),
              modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .border(4.dp, user_settings_bg, CircleShape)
            )
          }
        }
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, top = 8.dp)
      ) {
        Text(
          text = "mutual",
          style = Typography.h3.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
          ),
          textAlign = TextAlign.Start
        )
        Text(
          text = "#1234",
          style = Typography.h3.copy(
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color.Gray
          ),
          textAlign = TextAlign.Start
        )
      }
      Spacer(modifier = Modifier.height(16.dp))
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(colors.settingsBackground),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth(),
          verticalArrangement = Arrangement.Top,
          horizontalAlignment = Alignment.Start
        ) {
          UserSettingsList(userSettings = mutableListOf<SettingsEntity>().apply {
            add(
              SettingsEntity(
                title = stringResource(string.settings_set_status),
                icon = R.drawable.ic_baseline_account_circle_24,
                currentStatus = "Online"
              )
            )
            add(
              SettingsEntity(
                title = stringResource(string.settings_my_account),
                icon = R.drawable.ic_baseline_account_box_24,
              )
            )
            add(
              SettingsEntity(
                title = stringResource(string.settings_user_profile),
                icon = R.drawable.ic_baseline_edit_24,
              )
            )
            add(
              SettingsEntity(
                title = stringResource(string.settings_privacy),
                icon = R.drawable.ic_baseline_security_24,
              )
            )
            add(
              SettingsEntity(
                title = stringResource(string.settings_auth_apps),
                icon = R.drawable.ic_baseline_vpn_key_24,
              )
            )
            add(
              SettingsEntity(
                title = stringResource(string.settings_connections),
                icon = R.drawable.ic_baseline_laptop_24,
              )
            )
            add(
              SettingsEntity(
                title = stringResource(string.settings_scan_qr),
                icon = R.drawable.ic_baseline_qr_code_24,
              )
            )
          },
            nitroSettings = mutableListOf<SettingsEntity>().apply {
              add(
                SettingsEntity(
                  title = stringResource(string.settings_subscribe_today),
                  icon = R.drawable.ic_baseline_subscriptions_24
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_boosts),
                  icon = R.drawable.ic_baseline_account_box_24,
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_nitro_gifting),
                  icon = R.drawable.ic_baseline_card_giftcard_24,
                )
              )
            },
            appSettings = mutableListOf<SettingsEntity>().apply {
              add(
                SettingsEntity(
                  title = stringResource(string.settings_voice_video),
                  icon = R.drawable.ic_baseline_mic_24,
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_notification),
                  icon = R.drawable.ic_baseline_notification_important_24,
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_text_images),
                  icon = R.drawable.ic_baseline_image_24,
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_appearance),
                  icon = R.drawable.ic_baseline_color_lens_24,
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_accessibility),
                  icon = R.drawable.ic_baseline_accessibility_new_24,
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_behavior),
                  icon = R.drawable.ic_baseline_settings_applications_24,
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_language),
                  icon = R.drawable.ic_baseline_language_24,
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_activity_status),
                  icon = R.drawable.ic_baseline_vpn_key_24,
                )
              )
            },
            appInfo = mutableListOf<SettingsEntity>().apply {
              add(
                SettingsEntity(
                  title = stringResource(string.settings_change_log),
                  icon = R.drawable.ic_baseline_info_24
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_support),
                  icon = R.drawable.ic_baseline_contact_support_24,
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_debug_logs),
                  icon = R.drawable.ic_baseline_info_24,
                )
              )
              add(
                SettingsEntity(
                  title = stringResource(string.settings_acknowledgement),
                  icon = R.drawable.ic_baseline_info_24,
                )
              )
            })
        }
      }

    }
  }
}